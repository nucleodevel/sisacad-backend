package org.nucleodevel.sisacad.domain;

import java.io.Serializable;
import java.util.Objects;

public abstract class AbstractEntity<ID> implements Serializable, Comparable<AbstractEntity<ID>> {

	private static final long serialVersionUID = 291532512703681554L;

	public abstract ID getId();

	public abstract void setId(ID id);

	@Override
	public int hashCode() {
		return Objects.hash(getId());
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractEntity<ID> other = (AbstractEntity<ID>) obj;
		return Objects.equals(getId(), other.getId());
	}

	@Override
	public int compareTo(AbstractEntity<ID> o) {
		String str1 = this.toComparableString();
		String str2 = o.toComparableString();

		return str1.compareToIgnoreCase(str2);
	}

	public String toComparableString() {
		return String.format("%09d", getId());
	}

}
