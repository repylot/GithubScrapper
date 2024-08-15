package org.repylot.controller.scrapper.impl;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.repylot.controller.scrapper.Retriever;

import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlCleanerContentRetriever implements Retriever {
    @Override
    public String retrieve(String url) throws IOException {
        TagNode rootNode = null;

        try {
            Document document = Jsoup.connect(url).get();
            String rawLink = rawContentLink(document);

            HtmlCleaner cleaner = new HtmlCleaner();
            rootNode = cleaner.clean(new URL(rawLink));

        } catch (Exception e) {
            e.printStackTrace();

        }

        return extractTextWithStructure(rootNode, 0);

    }

    private String rawContentLink(Document doc) {
        Pattern pattern = Pattern.compile("\\\"rawBlobUrl\\\"\\:\\\"([\\w\\.\\s:/-]+)\\\"");
        Matcher matcher = pattern.matcher(doc.toString());

        matcher.find();
        return matcher.group(1);
    }

    private static String extractTextWithStructure(TagNode node, int indentLevel) {
        StringBuilder sb = new StringBuilder();
        String indent = "\t".repeat(indentLevel);

        for (Object child : node.getAllChildren()) {
            if (child instanceof TagNode) {
                TagNode tagNode = (TagNode) child;
                String tagName = tagNode.getName();

                if ("br".equalsIgnoreCase(tagName)) {
                    sb.append("\n").append(indent);
                } else if ("div".equalsIgnoreCase(tagName)) {
                    sb.append("\n").append(indent);
                    sb.append(extractTextWithStructure(tagNode, indentLevel));
                } else if ("ul".equalsIgnoreCase(tagName) || "ol".equalsIgnoreCase(tagName)) {
                    sb.append("\n").append(indent);
                    sb.append(extractTextWithStructure(tagNode, indentLevel + 1));
                } else if ("li".equalsIgnoreCase(tagName)) {
                    sb.append("\n").append(indent).append("- ");
                    sb.append(extractTextWithStructure(tagNode, indentLevel));
                } else if ("pre".equalsIgnoreCase(tagName)) {
                    sb.append("\n").append(indent);
                    sb.append(tagNode.getText());
                } else {
                    sb.append(extractTextWithStructure(tagNode, indentLevel));
                }
            } else {
                sb.append(indent).append(child.toString().trim());
            }
        }

        return sb.toString().trim();
    }
}
