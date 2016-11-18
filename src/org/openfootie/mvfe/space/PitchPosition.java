package org.openfootie.mvfe.space;

public class PitchPosition {
	
	private final LengthCoordinate lengthCoordinate;
	private final WidthCoordinate widthCoordinate;
	
	public PitchPosition(LengthCoordinate lengthCoordinate, WidthCoordinate widthCoordinate) {
		super();
		this.lengthCoordinate = lengthCoordinate;
		this.widthCoordinate = widthCoordinate;
	}

	public LengthCoordinate getLengthCoordinate() {
		return lengthCoordinate;
	}

	public WidthCoordinate getWidthCoordinate() {
		return widthCoordinate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((lengthCoordinate == null) ? 0 : lengthCoordinate.hashCode());
		result = prime * result + ((widthCoordinate == null) ? 0 : widthCoordinate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PitchPosition other = (PitchPosition) obj;
		if (lengthCoordinate != other.lengthCoordinate)
			return false;
		if (widthCoordinate != other.widthCoordinate)
			return false;
		return true;
	}
}
