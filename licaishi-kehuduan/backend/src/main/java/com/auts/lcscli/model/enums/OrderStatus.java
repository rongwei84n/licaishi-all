package com.auts.lcscli.model.enums;


public enum OrderStatus {
	WP("01", "带打款"),
	WC("02", "带结佣"),
	OC("03", "已结佣"),
	OF("99","已失败");
	
	private OrderStatus(String value, String text) {
		this.value = value;
		this.text = text;
	}

	private String value;
	private String text;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
