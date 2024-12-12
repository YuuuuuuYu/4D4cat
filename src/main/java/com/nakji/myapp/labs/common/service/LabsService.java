package com.nakji.myapp.labs.common.service;

import com.nakji.myapp.common.config.ThirdPartyProperties;
import com.nakji.myapp.labs.common.model.LabsInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LabsService {
    private static final String SEARCH = "search";

    private final ThirdPartyProperties secrets;

    public LabsInfo getThirdPartyInfo(String apiName) {
        if (apiName == null || apiName.trim().isEmpty())
            return new LabsInfo("", "", null, false);

        Map<String, Boolean> labsModuleInfo = Collections.singletonMap(apiName, true);
        String thirdPartyType = secrets.getThirdPartyType().getOrDefault(apiName, "");

        return new LabsInfo(apiName, thirdPartyType, labsModuleInfo, SEARCH.equals(thirdPartyType));
    }

    public List<String> getThirdPartyList() {
        return secrets.getThirdPartyList();
    }
}
