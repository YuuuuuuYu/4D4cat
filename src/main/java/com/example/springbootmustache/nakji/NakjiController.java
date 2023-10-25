package com.example.springbootmustache.nakji;

import com.example.springbootmustache.nakji.common.CommonService;
import com.example.springbootmustache.nakji.config.NakjiProperty;
import com.example.springbootmustache.nakji.model.PageView;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Controller
@RequestMapping("/nakji")
public class NakjiController {

    @Autowired
    NakjiProperty nakji;

    @Autowired
    CommonService commonService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("title", "Hello, World!");
        model.addAttribute("message", "This is a mustache template.");
        return "nakji/index";
    }

    @GetMapping("/api/naver")
    @ResponseBody
    public List<PageView> naverSearch() {
        JsonNode resultJson = null;
        JsonNode jsonMap = null;
        HttpURLConnection connection = null;
        List<PageView> returnPage = null;
        int responseCode = -1;

        try {
            connection = nakji.naverSearchConnection("", "");

            responseCode = connection.getResponseCode();
            resultJson = commonService.readBody(connection.getInputStream());

            jsonMap = resultJson.findValue("items");
            commonService.printJson(jsonMap.toString());

            returnPage = new ArrayList<>();
            if (jsonMap.isArray()) {
                Iterator<JsonNode> elements = jsonMap.elements();
                JsonNode item = null;
                while (elements.hasNext()) {
                    item = elements.next();
                    returnPage.add(new PageView(item.get("title").toString(), item.get("description").toString(), item.get("link").toString()));
                }
            }
        } catch(Exception e) {
          e.printStackTrace();

        } finally {
            if (connection != null) connection.disconnect();
        }

        return returnPage;
    }

    @PostMapping("/convert")
    @ResponseBody
    public PageView convert(@RequestBody PageView pageView) {

        return pageView;
    }
}
