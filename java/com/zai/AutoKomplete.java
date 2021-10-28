package com.zai;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.stream.Stream;
import java.util.TreeSet;

public class AutoKomplete {

    HashMap<String, TreeSet<String>> database;
    HashMap<String, Integer> frequencies;

    public AutoKomplete(int k, String filepath, boolean isRaw) throws IOException {
        
        try (
            FileReader fr = new FileReader(filepath);    
            BufferedReader br = new BufferedReader(fr);
            Stream<String> stream = Files.lines(
                Paths.get(filepath), StandardCharsets.UTF_8
            )
        ) {
            int lineCount = (int)stream.count();

            frequencies = new HashMap<String, Integer>(lineCount, 0.85f);
            database = new HashMap<String, TreeSet<String>>(lineCount * 10, 0.85f);

            String line;
            while ((line = br.readLine()) != null) {

                String[] split = line.split(",", 2);
                String key = KoreanDivider.decompose(split[0]);
                String value = split[0];

                frequencies.put(value, Integer.parseInt(split[1]));

                for (int len = 1; len <= key.length(); len++) {
                    String subkey = key.substring(0, len);

                    database.putIfAbsent(
                        subkey, new TreeSet<String>(new Comparator<String>() {
                            public int compare(String a, String b)
                            {
                                return -Integer.compare(
                                    frequencies.get(a), frequencies.get(b)
                                );
                            }
                        })
                    );

                    TreeSet<String> recommendations = database.get(subkey);
                    recommendations.add(value);
                    if (recommendations.size() > k)
                        recommendations.pollLast();
                }
            }
        }
    }
    
    public ArrayList<String> autokomplete(String query) {
        String decomposed = KoreanDivider.decompose(query);
        return new ArrayList<String>(database.get(decomposed));
    }
}
