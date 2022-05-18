import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Dictionary
{
    private Set<String> wordsSet;

    public Dictionary() throws IOException
    {
        Path path = Paths.get("wordle-answers-alphabetical.txt");
        byte[] readBytes = Files.readAllBytes(path);
        String wordListContents = new String(readBytes, "UTF-8");
        String[] words = wordListContents.split("\n");
        wordsSet = new HashSet<>();
        Collections.addAll(wordsSet, words);
    }

    public boolean contains(String word)
    {
        return wordsSet.contains(word);
    }
}