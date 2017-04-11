package ch.portmann.feature;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.stanford.nlp.trees.GrammaticalRelation;
import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.GrammaticalStructureFactory;
import edu.stanford.nlp.trees.PennTreebankLanguagePack;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations;
import edu.stanford.nlp.trees.TreebankLanguagePack;
import edu.stanford.nlp.trees.TypedDependency;
import edu.stanford.nlp.util.CoreMap;

/**
 * Created by Ming Deng on 5/9/2016.
 */
public class TenseFeature {

    private Map<String, List<String>> tense = new HashMap<>(); // (tense name - POS pair), use the POS to determine the tense of the word
    private Tree tree;
    private String senttense = "NULL.";
    private Map<String, String> dep_word = new HashMap<>();
    private List<String> typedDep = new ArrayList<>();  //"typedDep" contains the typed dependencies which determine the tense of sentence.

    public TenseFeature(){
        typedDep.addAll(Arrays.asList("aux","auxpass","root","cop"));
        List<String> present = Arrays.asList("VB","VBP","VBZ","VBG","MD");
        List<String> past = Arrays.asList("VBD","VBN");
        List<String> pastModal = Arrays.asList("could","would");
        List<String> futureModal = Arrays.asList("will","'ll","wo");
        List<String> verb = Arrays.asList("aux","cop","auxpass");
        tense.put("Present", present);
        tense.put("Past", past);
        tense.put("PastModal", pastModal);
        tense.put("FutureModal", futureModal);
        tense.put("Verb", verb);
    }

    public TenseFeature(CoreMap sentence) {

        this.tree = sentence.get(TreeCoreAnnotations.TreeAnnotation.class);
        typedDep.addAll(Arrays.asList("aux","auxpass","root","cop"));
        List<String> present = Arrays.asList("VB","VBP","VBZ","VBG","MD");
        List<String> past = Arrays.asList("VBD","VBN");
        List<String> pastModal = Arrays.asList("could","would");
        List<String> futureModal = Arrays.asList("will","'ll","wo");
        List<String> verb = Arrays.asList("aux","cop","auxpass");
        tense.put("Present", present);
        tense.put("Past", past);
        tense.put("PastModal", pastModal);
        tense.put("FutureModal", futureModal);
        tense.put("Verb", verb);

    }

    public void setTree(CoreMap sentence){
        this.tree = sentence.get(TreeCoreAnnotations.TreeAnnotation.class);
    }

    //Input: tree with nodes(NP,VP)
    //Output: the tense of the sentence
    //Find the tense of current layer

    public Map<String, String> getVerb(Tree tree) {

        TreebankLanguagePack tlp = new PennTreebankLanguagePack();
        GrammaticalStructureFactory gsf = tlp.grammaticalStructureFactory();
        GrammaticalStructure gs = gsf.newGrammaticalStructure(tree);
        Collection<TypedDependency> td = gs.typedDependenciesCollapsed();

        for (TypedDependency td1 : td) {
//            System.out.println("----------------------------------------Begin-----------------------------------------");
//            System.out.println(td1);
//            System.out.println("-------------------------------------Relation-----------------------------------------");
            GrammaticalRelation relation = td1.reln();
            if (typedDep.contains(relation.getShortName()) && !dep_word.containsKey(relation.getShortName() + "POS")) {
//                System.out.printf(td1.dep() + "---------------------------------Found %s--------------------------------\n",
//                        relation.getShortName());
                dep_word.put(relation.getShortName(), td1.dep().value());           //(typedDep,word)
                dep_word.put(relation.getShortName() + "POS", td1.dep().tag());     //(typedDepPOS,POS)
            }
//            System.out.println("relation.getShortName(): " + relation.getShortName());
//            System.out.println("relation.getLongName(): " + relation.getLongName());
//            System.out.println("relation.toString(): " + relation.toString());
//            System.out.println("relation.toPrettyString(): " + relation.toPrettyString());
//            System.out.println("------------------------------------Governor-----------------------------------------");
//            IndexedWord governor = td1.gov();
//            System.out.println(governor);
//            System.out.println(governor.value());
//            System.out.println(governor.tag());
//            System.out.println("------------------------------------Dependent-----------------------------------------");
//            IndexedWord dependent = td1.dep();
//            System.out.println(dependent);
//            System.out.println(dependent.value());
//            System.out.println(dependent.tag());
//            System.out.println("----------------------------------------End-------------------------------------------");

        }
        return dep_word;
    }

    //    public Map<String,String> getTense(){
    public String getTense() {

        Tree senttree = this.tree;
        Map<String, String> Verb = getVerb(senttree);
//        System.out.println(Verb);
        if (Verb.containsKey("aux")) {
            switch (Verb.get("auxPOS")) {
                case "MD": {
                    switch (Verb.get("aux")) {
                        case "could":
                        case "would":
                            senttense = "Past";
                            break;
                        case "can":
                            senttense = "Present";
                            break;
                        case "will":
                            senttense = "Future";
                            break;
                    }
                    break;
                }
                case "VB":
                case "VBP":
                case "VBZ":
                case "VBG":
                    senttense = "Present";
                    break;
                case "VBD":
                    senttense = "Past";
                    break;
                default:
                    senttense = "NULL";
            }
        } else if (Verb.containsKey("auxpass")) {
            switch (Verb.get("auxpassPOS")) {
                case "VB":
                case "VBP":
                case "VBZ":
                case "VBG":
                    senttense = "Present";
                    break;
                case "VBD":
                    senttense = "Past";
                    break;
                default:
                    senttense = "NULL";
            }
        } else if (Verb.containsKey("cop")) {
            switch (Verb.get("copPOS")) {
                case "VB":
                case "VBP":
                case "VBZ":
                case "VBG":
                    senttense = "Present";
                    break;
                case "VBD":
                    senttense = "Past";
                    break;
                default:
                    senttense = "NULL";
            }
        } else if (Verb.containsKey("root")) {
            switch (Verb.get("rootPOS")) {
                case "VB":
                case "VBP":
                case "VBZ":
                case "VBG":
                    senttense = "Present";
                    break;
                case "VBD":
                    senttense = "Past";
                    break;
                default:
                    senttense = "NULL";
            }
        }

        return senttense;

    }
}
