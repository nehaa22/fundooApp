package com.bridgeit.fundooapp.note.dto;

public class NotesDTO {
	
	private String title;
	private String description;
	private String color;
	
	public NotesDTO() {
		super();
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	@Override
	public String toString() {
		return "NotesDto [title=" + title + ", description=" + description + ", color=" + color + "]";
	}

	
	

	
}
