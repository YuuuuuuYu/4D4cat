package com.nakji.myapp.labs.common.model;

import java.util.Map;

public record LabsInfo(String thirdPartyName, String thirdPartyType, Map<String, Boolean> labsModuleInfo, boolean isSearch) {}
