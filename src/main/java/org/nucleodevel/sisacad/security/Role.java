package org.nucleodevel.sisacad.security;

public enum Role {

	ADMIN("ROLE_ADMIN", 1000), USER("ROLE_USER", 1);

	private String tag;
	private int priority;

	private Role(String tag, int priority) {
		this.tag = tag;
		this.priority = priority;
	}

	public String getTag() {
		return tag;
	}

	public int getPriority() {
		return priority;
	}

	public static Role getByTag(String tag) {
		for (Role role : values()) {
			if (role.getTag().equals(tag)) {
				return role;
			}
		}

		return null;
	}

}
