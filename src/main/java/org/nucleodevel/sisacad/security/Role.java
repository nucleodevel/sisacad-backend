package org.nucleodevel.sisacad.security;

public enum Role {

	ADMIN("ROLE_ADMIN", 1000), PEDAGOGICO("ROLE_PEDAGOGICO", 100), GRADUACAO("ROLE_GRADUACAO", 100), RH("ROLE_RH", 100),
	DISCENTE("ROLE_DISCENTE", 10), DOCENTE("ROLE_DOCENTE", 10), VESTIBULANDO("ROLE_VESTIBULANDO", 10),
	USER("ROLE_USER", 1);

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
