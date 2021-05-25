package com.example.service2.model;

import java.io.*;

public class InputOutput {
    public static class Reader {
        public static int lastName = 0;

        protected String setName() {
            String line = "";
            try {
                File file = new File("src/main/resources/text.txt");
                //создаем объект FileReader для объекта File
                FileReader fr = new FileReader(file);
                //создаем BufferedReader с существующего FileReader для построчного считывания
                BufferedReader reader = new BufferedReader(fr);
                // считаем сначала первую строку

                int count = 0;
                while (true) {
                    if (count == lastName) {
                        lastName++;
                        line = reader.readLine();
                        break;
                    }
                    reader.readLine();
                    count++;
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return line;
        }

    }
}
