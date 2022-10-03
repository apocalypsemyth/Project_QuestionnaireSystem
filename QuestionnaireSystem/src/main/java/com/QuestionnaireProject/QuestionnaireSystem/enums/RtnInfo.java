package com.QuestionnaireProject.QuestionnaireSystem.enums;

public enum RtnInfo {
	SUCCESSFUL("200", "�����B"),
	PARAMETER_REQUIRED("400", "�K�v�ҝ�㞎��C���ď����B"),
	NOT_FOUND("404", "���Q�������B"),
	FAILED("500", "ᢐ�����C���ď����B"),
	DATA_REQUIRED("417", "���U�������I�����B"),
	SESSION_EXPIRED("512", "���쎞�ԉߒ��C���ď����B"),
	// �X�V�͎����C�����S�������C������W�L�����C���L�^�I���{��Session�B
	NOT_EMPTY_EMPTY("666", "Update mode empty"),
	ONE_QUESTION("667", "��������������B"),
	ONE_REQUIRED_QUESTION("668", "������������K�U���B"),
	JUST_ONE_QUESTION("669", "���\��������B"),
	NAME_OF_COMMON_QUESTION_IS_CUSTOMIZED_QUESTION("670", "��p��葴���i�s���׎������B");
	
	private String code;
	
	private String message;
	
	RtnInfo(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

}
