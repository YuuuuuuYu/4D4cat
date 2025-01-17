package com.nakji.myapp.labs.common.service;

import com.nakji.myapp.common.property.ThirdPartyProperties;
import com.nakji.myapp.labs.common.model.LabsInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LabsService {
    private static final String SEARCH = "search";

    private final ThirdPartyProperties secrets;

    public LabsInfo getThirdPartyInfo(String apiName) {
        if (apiName == null || apiName.trim().isEmpty())
            return new LabsInfo("", false);

        String thirdPartyType = secrets.getThirdPartyType().getOrDefault(apiName, "");
        return new LabsInfo(apiName, SEARCH.equals(thirdPartyType));
    }

    public List<String> getThirdPartyList() {
        return secrets.getThirdPartyList();
    }
}
