package com.nakji.myapp.main;

import com.nakji.myapp.api.blogFeed.service.BlogFeedService;
import com.nakji.myapp.labs.common.model.LabsInfo;
import com.nakji.myapp.labs.common.service.LabsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final BlogFeedService blogFeedService;
    private final LabsService labsService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("title", "Recent");
        model.addAttribute("rssData", blogFeedService.requestBlogFeed());

        return "main";
    }

    @GetMapping("/labs")
    public String labs(Model model, @RequestParam(name="api", required=false) String apiName) {
        LabsInfo labsInfo = labsService.getThirdPartyInfo(apiName);

        model.addAttribute("title", "Labs");
        model.addAttribute("thirdPartyName", labsInfo.thirdPartyName());
        model.addAttribute("thirdPartyType", labsInfo.thirdPartyType());
        model.addAttribute("module", labsInfo.labsModuleInfo());
        model.addAttribute("isSearch", labsInfo.isSearch());
        model.addAttribute("thirdPartyList", labsService.getThirdPartyList());

        return "labs";
    }

    @GetMapping("/trend")
    public String trend(Model model) {
        model.addAttribute("title", "Trend");
        return "trend";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("title", "About");
        return "about";
    }
}