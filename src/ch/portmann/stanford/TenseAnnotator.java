package ch.portmann.stanford;

import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import ch.portmann.feature.TenseFeature;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.Annotator;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.util.PropertiesUtils;

/**
 * Created by Ming Deng on 7/22/2016.
 */
// UHJO: "Annotator" is an interface defined by Stanford - so we are required to follow certain rules when defining a custom annotator  
public class TenseAnnotator implements Annotator{

    private TenseFeature TENSE;
    private final boolean VERBOSE;
    private final int nThreads;
    private final long maxTime;
    private final int maxSentenceLength;

    public TenseAnnotator(){this(true);}

    public TenseAnnotator(boolean verbose){
        this(new TenseFeature(),verbose,1,0);
    }

    public TenseAnnotator(TenseFeature tense, boolean verbose, int nThreads, long maxTime){
        this(tense,verbose,nThreads,maxTime,Integer.MAX_VALUE);
    }

    public TenseAnnotator(TenseFeature tense, boolean verbose, int nThreads, long maxTime, int maxSentenceLength){
        TENSE = tense;
        VERBOSE = verbose;
        this.nThreads = nThreads;
        this.maxTime = maxTime;
        this.maxSentenceLength = maxSentenceLength;
    }

    public TenseAnnotator(String annotatorName, Properties props) {
        VERBOSE = PropertiesUtils.getBool(props, annotatorName + ".verbose", false);
        this.maxSentenceLength = PropertiesUtils.getInt(props, annotatorName + ".maxlen", Integer.MAX_VALUE);
        this.nThreads = PropertiesUtils.getInt(props, annotatorName + ".nthreads", PropertiesUtils.getInt(props, "nthreads", 1));
        this.maxTime = 10;
    }

    @Override
    public void annotate(Annotation annotation) {
        if (VERBOSE) {
            System.err.print("Adding Tense annotation ... ");
        }
            for (CoreMap sent : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
                doOneSentence(sent);
            }
        if (VERBOSE) {
            System.err.println("done.");
        }
    }

    // UHJO: Ming says that this is the only important part of the class
    private CoreMap doOneSentence(CoreMap sentence) {
        String SentTence = new TenseFeature(sentence).getTense();
                List<CoreLabel> tokens = sentence.get(CoreAnnotations.TokensAnnotation.class);
        for (CoreLabel token : tokens) {
            token.set(TenseAnnotation.class, SentTence);
        }
        return sentence;
    }


    protected int nThreads() {
        return nThreads;
    }

    protected long maxTime() {
        return maxTime;
    }

    @Override
    public Set<Requirement> requirementsSatisfied() {
        return Annotator.REQUIREMENTS.get(STANFORD_POS);
    }

    @Override
    public Set<Requirement> requires() {
        return Collections.singleton(POS_REQUIREMENT);
    }
}
