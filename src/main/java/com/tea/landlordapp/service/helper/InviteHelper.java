package com.tea.landlordapp.service.helper;

import java.util.Map;

import com.tea.landlordapp.domain.AnonymousUser;

public interface InviteHelper {

	public Map<String, String>  buildInfoMap(AnonymousUser au);
}
