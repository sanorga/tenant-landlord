package com.tea.landlordapp.service.helper;

import java.util.Map;

import com.tea.landlordapp.domain.AnonymousUser;

public interface InviteHelper {

	public Map<String, String> buildPropertyInfoMap(AnonymousUser au);

	public Map<String, String> buildApplicationInfoMap(AnonymousUser au, String propertyIdStr);
}
