package org.example.API.component;

import org.example.API.controller.PersonController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
public class CategoryOrder {

    Logger logger = LoggerFactory.getLogger(PersonController.class);

    private List<String> listCategories = new ArrayList<String>();

    String rssFeedCategoriesGerman = "\"1\";\"Politik\"\n" +
            "\"2\";\"Wirtschaft, Finanzen, Immobilien\"\n" +
            "\"3\";\"Kultur, Geschichte und Feuilleton\"\n" +
            "\"4\";\"IT, Internet, Digitales, Technik, Technologie\"\n" +
            "\"5\";\"Wissenschaft, Wissen\"\n" +
            "\"6\";\"Gesellschaft, Panorama, Öko, Mobilität, Medien\"\n" +
            "\"7\";\"Arbeit, Hochschule, Karriere, Job\"\n" +
            "\"8\";\"Gesundheit\"\n" +
            "\"9\";\"Lokalnachrichten, Regionales, Lokales\"\n" +
            "\"10\";\"News\"\n" +
            "\"11\";\"Boulevard, Unterhaltung, Fernsehen\"\n" +
            "\"12\";\"Food, Mode, Reise, Urlaub, Lifestyle, Leben, Ratgeber\"\n" +
            "\"13\";\"Sport\"\n" +
            "\"14\";\"Auto, Motor\"";

    String rssFeedCategoriesEnglish = "\"1\";\"News, Latest Stories\"\n" +
            "\"2\";\"Business, Money, Economy, Real Estate, Finance\"\n" +
            "\"3\";\"Education, History, College, Culture, Arts\"\n" +
            "\"4\";\"Health, Science, Technology, Auto, Space, Nature\"\n" +
            "\"5\";\"Opinion, Comment, Views\"\n" +
            "\"6\";\"Entertainment, Lifestyle, Fashion, Beauty, Food\"\n" +
            "\"7\";\"Media, Showbiz, TV, Radio, Celebrities\"\n" +
            "\"8\";\"Sports\"\n" +
            "\"9\";\"Travel\"\n" +
            "\"10\";\"Miscellaneous\"";

    @Value("${language}")
    private String language;

    @PostConstruct
    public void init() {
        logger.info("Language for Categories is " + language);

        if (language.equals("en")){
            listCategories = parseCategories(rssFeedCategoriesEnglish);
        }else{
            listCategories = parseCategories(rssFeedCategoriesGerman);
        }
    }

    public List<String> parseCategories(String rssFeedCategories){

        String lines[] = rssFeedCategories.split("\\r?\\n");

        for (String line:lines){

                String [] feeds = line.split(";(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                for(int i = 0; i < feeds.length; i++){
                    //Cut of first and last char
                    feeds[i] = feeds[i].substring(1);
                    feeds[i] = feeds[i].substring(0, feeds[i].length() - 1);
                }
                logger.debug("NR " + feeds[0] + " Category " + feeds[1]);

                listCategories.add(feeds[1]);
        }

        return listCategories;
    }

    public void setListCategories(List<String> listCategories) {
        this.listCategories = listCategories;
    }

    public List<String> getListCategories() throws Exception {

        if(listCategories.isEmpty()) throw new Exception("List of Categories should not be null");

        return listCategories;
    }
}
