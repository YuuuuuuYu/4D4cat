package com.nakji.myapp.api.blogFeed.service;

import com.nakji.myapp.api.blogFeed.model.BlogPost;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class BlogFeedService {
    private static final String FEED_URL = "https://yuuuuuuyu.github.io/feed.xml";

    public List<BlogPost> requestBlogFeed() {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                                        .uri(URI.create(FEED_URL))
                                        .GET()
                                        .build();
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();
            return parseAndProcessXml(responseBody);

        } catch (IOException | InterruptedException e) {
            log.error("BlogFeedService.requestBlogFeed Error: {}", e.getMessage());
            return new ArrayList<>();
        }
    }

    protected List<BlogPost> parseAndProcessXml(String xmlContent) {
        List<BlogPost> blogPosts = new ArrayList<>();
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            DefaultHandler handler = new DefaultHandler() {
                boolean isTitle = false;
                BlogPost currentPost = null;
                String postTitle = null;
                String postLink = null;

                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) {
                    if (qName.equalsIgnoreCase("title")) {
                        isTitle = true;
                    } else if (qName.equalsIgnoreCase("link")) {
                        String href = attributes.getValue("href");
                        if (href != null) {
                            postLink = href.trim();
                        }
                    }
                }

                @Override
                public void characters(char[] ch, int start, int length) {
                    if (isTitle) {
                        postTitle = new String(ch, start, length).trim();
                        isTitle = false;
                    }
                }

                @Override
                public void endElement(String uri, String localName, String qName) {
                    if (qName.equalsIgnoreCase("entry") && postTitle != null && postLink != null) {
                        currentPost = new BlogPost(postTitle, postLink);
                        blogPosts.add(currentPost);
                        currentPost = null;
                    }
                }
            };

            InputSource inputSource = new InputSource(new StringReader(xmlContent));
            saxParser.parse(inputSource, handler);

        } catch (Exception e) {
            log.error("BlogFeedService.parseAndProcessXml Error: {}", e.getMessage());
        }
        return blogPosts;
    }
}