
/**
 * InformationRetrieval
 */
import java.io.*;
import java.time.Duration;
import java.time.Instant;
import java.util.*;


public class InformationRetrieval {

    static final int total_size=26;
    
    static class TrieNode{

        TrieNode[] children=new TrieNode[total_size];
        boolean isEndOfWord; 
        int frequency;

        TrieNode(){
            isEndOfWord =false;
            for(int i=0;i<total_size;i++)
            {
                children[i]=null;
            }
            frequency=0;
        }
    };


        static TrieNode root;
        static List<TrieNode> documentRoots;


        static void insertWord(String key,TrieNode root){
            int level; 
            int length = key.length(); 
            int index; 
            // System.out.println(key);
        
            TrieNode traveller = root; 
        
            for (level = 0; level < length; level++) 
            { 
                index = key.charAt(level) - 'a'; 
                if(index>26||index<0)
                { traveller.isEndOfWord=true;
                  traveller.frequency++;
                }
               
                if (traveller.children[index] == null) 
                    traveller.children[index] = new TrieNode(); 
        
                traveller = traveller.children[index]; 
            } 

            traveller.isEndOfWord = true;
            traveller.frequency++;
        }

        

        static TrieNode searchWord(String key,TrieNode root){
            int level;
            int index=0;
            TrieNode traveller=root;
            int length=key.length();
            for( level=0;level<length;level++)
            { 
                index=key.charAt(level)-'a';
                if(traveller.children[index]==null)
                {
                    TrieNode notfound=null;
                    return notfound;
                }

             traveller=traveller.children[index];
            }

            if(traveller.isEndOfWord==true)
                return traveller;
            else
            {
                TrieNode notfound=null;
                return notfound;
            }
        }


        static int findFrequency(String key,TrieNode root)
        {
            TrieNode found=searchWord(key,root);
            if(found!=null)
            return found.frequency;
            else
            {
                return 0;
            }

        }

        static List<String> lexicalTokenizer(String sentence)
        {
            List<String> items = Arrays.asList(sentence.split(" "));
            return items;
        }

        static TrieNode buildTrie(List <String> keys)
        {
            TrieNode root=new TrieNode();
            for(int i=0;i<keys.size();i++)
            {
                insertWord(keys.get(i),root);
            }

            return root;
        }

        static void showVocabularyStats(List<String> keys,TrieNode root)
        {
            for(int i=0;i<keys.size();i++)
            {
                TrieNode found=searchWord(keys.get(i),root);
                 if(found!=null)
                 {
                System.out.println("Frequency of " +keys.get(i)+" :"+found.frequency);
                 }

                else
                 {
                System.out.println(keys.get(i)+": Notfound");
                 }
            }
            
        }

        static List<String> linesFromDocs(String path)
        {
            List<String> result = new ArrayList<>();
            BufferedReader br = null;
    
            try {
    
                br = new BufferedReader(new FileReader(path));
    
                String line;
                while ((line = br.readLine()) != null) {
                    if(line.length()>0)
                    {
                        String newLine=line.trim();
                        newLine=newLine.replaceAll(".", " ");
                        if(line.trim().length()>=2)
                        result.add(line.toLowerCase().replaceAll("[0123456789’’:.,'-()!&\\\\$%@?/_]", " ").trim());
                    }
                   
                }
    
            } catch (IOException e) {
                e.printStackTrace();
            } 
            return result;
        }



        static List<String> keyTokenizer(List<String> docLines)
        {   

            ArrayList<String> stopwords=new ArrayList<String>(Arrays.asList("without", "see", "unless", "due", "also", "must", "might", "like", "]", "[", "}", "{", "<", ">", "?", "\\", "\\", "/", ")", "(", "will", "may", "can", "much", "every", "the", "in", "other", "this", "the", "many", "any", "an", "or", "for", "in", "an", "an ", "is", "a", "about", "above", "after", "again", "against", "all", "am", "an", "and", "any", "are", "aren’t", "as", "at", "be", "because", "been", "before", "being", "below", "between", "both", "but", "by", "can’t", "cannot", "could",
            "couldn’t", "did", "didn’t", "do", "does", "doesn’t", "doing", "don’t", "down", "during", "each", "few", "for", "from", "further", "had", "hadn’t", "has", "hasn’t", "have", "haven’t", "having",
            "he", "he’d", "he’ll", "he’s", "her", "here", "here’s", "hers", "herself", "him", "himself", "his", "how", "how’s", "i ", " i", "i’d", "i’ll", "i’m", "i’ve", "if", "in", "into", "is",
            "isn’t", "it", "it’s", "its", "itself", "let’s", "me", "more", "most", "mustn’t", "my", "myself", "no", "nor", "not", "of", "off", "on", "once", "only", "ought", "our", "ours", "ourselves",
            "out", "over", "own", "same", "shan’t", "she", "she’d", "she’ll", "she’s", "should", "shouldn’t", "so", "some", "such", "than",
            "that", "that’s", "their", "theirs", "them", "themselves", "then", "there", "there’s", "these", "they", "they’d", "they’ll", "they’re", "they’ve",
            "this", "those", "through", "to", "too", "under", "until", "up", "very", "was", "wasn’t", "we", "we’d", "we’ll", "we’re", "we’ve",
            "were", "weren’t", "what", "what’s", "when", "when’s", "where", "where’s", "which", "while", "who", "who’s", "whom",
            "why", "why’s", "with", "won’t", "would", "wouldn’t", "you", "you’d", "you’ll", "you’re", "you’ve", "your", "yours", "yourself", "yourselves",
            "without", "see", "unless", "due", "also", "must", "might", "like", "will", "may", "can", "much", "every", "the", "in", "other", "this", "the", "many", "any", "an", "or", "for", "in", "an", "an ", "Is", "A", "About", "Above", "After", "Again", "Against", "All", "Am", "An", "And", "Any", "Are", "Aren’t", "As", "At", "Be", "Because", "Been", "Before", "Being", "Below", "Between", "Both", "But", "By", "Can’t", "Cannot", "Could",
            "Couldn’t", "did", "Didn’t", "Do", "Does", "Doesn’t", "Doing", "Don’t", "Down", "During", "Each", "Few", "For", "From", "Further", "Had", "Hadn’t", "Has", "Hasn’t", "Have", "Haven’t", "Having",
            "He", "He’d", "He’ll", "He’s", "Her", "Here", "Here’s", "Hers", "Herself", "Him", "Himself", "His", "How", "How’s", "I ", " I", "I’d", "I’ll", "I’m", "I’ve", "If", "In", "Into", "Is",
            "Isn’t", "It", "It’s", "Its", "Itself", "Let’s", "Me", "More", "Most", "Mustn’t", "My", "Myself", "No", "Nor", "Not", "Of", "Off", "On", "Once", "Only", "Ought", "Our", "Ours", "Ourselves",
            "Out", "Over", "Own", "Same", "Shan’t", "She", "She’d", "She’ll", "She’s", "Should", "Shouldn’t", "So", "Some", "Such", "Than",
            "That", "That’s", "Their", "Theirs", "Them", "Themselves", "Then", "There", "There’s", "These", "They", "They’d", "They’ll", "They’re", "They’ve",
            "This", "Those", "Through", "To", "Too", "Under", "Until", "Up", "Very", "Was", "Wasn’t", "We", "We’d", "We’ll", "We’re", "We’ve",
            "Were", "Weren’t", "What", "What’s", "When", "When’s", "Where", "Where’s", "Which", "While", "Who", "Who’s", "Whom",
            "Why", "Why’s", "With", "Won’t", "Would", "Wouldn’t", "You", "You’d", "You’ll", "You’re", "You’ve", "Your", "Yours", "Yourself", "Yourselves","i"," "));    
            
            for(int i=0;i<stopwords.size();i++)
            stopwords.set(i, stopwords.get(i).toLowerCase());

            List<String> result=new ArrayList<String>();
            for(int i=0;i<docLines.size();i++)
            {
             ArrayList<String>delimited=new ArrayList<String>(Arrays.asList(docLines.get(i).split("[ -]")));
             delimited.removeAll(stopwords);
           
            
             for (int j = 0; j <delimited.size(); j++) {
                delimited.set(j,delimited.get(j).replaceAll("\\[", "").replaceAll("\\]", ""));
               
                 delimited.set(j,delimited.get(j).replaceAll("[:.,'-()!&\\\\$%@?/_]", ""));
                 delimited.set(j,delimited.get(j).replaceAll("[^\\p{ASCII}]", ""));
                //  PorterStemmer pStemmer=new  PorterStemmer();
                //  char [] toStem=delimited.get(j).toCharArray();
                //  for(int k=0;k<toStem.length;k++)
                //  {
                //     pStemmer.add(toStem[k]);
                //  }
                //  pStemmer.stem();
                //  String stemmed=pStemmer.toString();
        
                if(delimited.get(j).isEmpty()!=true&&delimited.get(j).length()>1)
                { 
                    result.add(delimited.get(j));
                    
                }

             }
            }
            return result;
        }

        static List<String> getVocabulary(List<String> words)
        {
            Set <String> hset=new HashSet<String>();
            for(int i=0;i<words.size();i++)
            if(words.get(i).length()!=1)
            {
                hset.add(words.get(i));
            }
            List <String> vocabulary=new ArrayList<String>(hset);
            Collections.sort(vocabulary);
            return vocabulary;
        }


        static class Document{
            String docName;
            TrieNode docTrieRoot;
            List<String> words;
            List<String> lines;



            Document(String dname){
                lines=linesFromDocs("ths-181-dataset/th-dataset/"+dname);
                List<String> tokenized=keyTokenizer(lines);
               

                words=getVocabulary(tokenized);
                docTrieRoot =buildTrie(tokenized);
            }
        }


        static class Pair{
            int first;
            int second;

            Pair(int x,int y)
            {
                first=x;
                second=y;
            }
        }
        


        static final long MEGABYTE = 1024L * 1024L;

        static long bytesToMegabytes(long bytes) {
           return bytes / MEGABYTE;
       }
       
        

        public static void main(String[] args) {

            Instant start = Instant.now();
            ArrayList<Document> documents=new ArrayList<Document>();
            File dataset= new File("ths-181-dataset\\th-dataset");
            HashMap<String,ArrayList<String>> inverted_index=new HashMap<String,ArrayList<String>>();
            System.out.println("Crunching through the Dataset ....");
            String[] files=dataset.list();
            System.out.print("Progress :|");
             for(int i=0;i<files.length;i++)
             { if(i%5==0)
                 System.out.print("#");
                Document d=new Document(files[i]);
                
                documents.add(d);
                for(int j=0;j<d.words.size();j++)
                {   
                   if(inverted_index.get(d.words.get(j))==null)
                   inverted_index.put(d.words.get(j), new ArrayList<String>());
                   StringBuilder x1=new StringBuilder();
                   x1.append("{").append(i+1).append("=").append(findFrequency(d.words.get(j), d.docTrieRoot));
                   inverted_index.get(d.words.get(j)).add(x1.toString());
                }
                
             }
             System.out.println("|");
             System.out.println("Inverted Index Created in Output.txt");

            
             
            
             
             File file = new File("./output.txt");
        
             BufferedWriter bf = null;;
             
             try{
                 
                
                 bf = new BufferedWriter( new FileWriter(file) );
      
                
                 TreeMap<String, ArrayList<String>> sorted = new TreeMap<>(); 
  
                
                 sorted.putAll(inverted_index); 
                 for(Map.Entry<String, ArrayList<String>> entry : sorted.entrySet()){
                     
                     bf.write( entry.getKey()+" "+entry.getValue().size()+":" + entry.getValue() );
                     
                     //new line
                     bf.newLine();
                 }
                 
                 bf.flush();
      
             }catch(IOException e){
                 e.printStackTrace();
             }finally{
                 
                 try{
                     bf.close();
                 }catch(Exception e){}
             }

            //  Runtime runtime = Runtime.getRuntime();
            //  long memory = runtime.totalMemory() - runtime.freeMemory();
            //  System.out.println("Used memory is bytes: " + memory);
            //  System.out.println("Used memory is megabytes: "
            //          + bytesToMegabytes(memory));

             Instant end= Instant.now();
             Duration timeElapsed = Duration.between(start, end); 
             System.out.println("Time Taken : "+timeElapsed.toMillis()+"milliseconds");
            
             
             
            
            
            
            
           

                

            
            



           
            
            
            
        }

    }

    
