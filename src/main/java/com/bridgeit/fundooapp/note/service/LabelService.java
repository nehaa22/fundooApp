package com.bridgeit.fundooapp.note.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bridgeit.fundooapp.response.Response;
import com.bridgeit.fundooapp.note.dto.LabelDTO;
import com.bridgeit.fundooapp.note.dto.NotesDTO;
import com.bridgeit.fundooapp.note.model.Label;

@Service
public interface LabelService 
{
	public Response createLabel(LabelDTO labelDto , String token);
	public Response deleteLabel(long labelId ,String token);
	public Response addLabelToNote(long labelId ,String token , long noteId);
	public Response removeLabelFromNote(long labelId ,String token , long noteId);
	public Response updateLabel(long labelId , String token , LabelDTO labelDto);
	public List<Label> getAllLabel(String token);
	public List<Label> getLebelsOfNote(String token , long noteId);
	public List<NotesDTO> getNotesOfLabel(String token , long labelId );

}
