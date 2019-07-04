package com.bridgeit.fundooapp.note.service;

import java.util.List;
import java.util.Set;

import com.bridgeit.fundooapp.note.dto.NotesDTO;
import com.bridgeit.fundooapp.response.Response;
import com.bridgeit.fundooapp.note.model.Note;
import com.bridgeit.fundooapp.user.model.User;

public interface NoteService 
{
	public Response createNote(NotesDTO notesDto ,String token);
	public Response updateNote(NotesDTO notesDto,String token,long noteId);
	public Response delete(String token , long noteId);
	public Response pinAndUnPin(String token , long noteId);
	public Response archiveAndUnArchive(String token , long noteId);
	public Response trashAndUnTrash(String token , long noteId);
	public Response deletePermanently(String token , long noteId);
	public Response setColor(String token , String colorCode , long noteId);
	public Response addCollaborator(String token,String email,long noteId);
	public Response removeCollaborator(String token,String email,long noteId);
	public Response addReminder(String token, long noteId , String reminder);
    public Response removeReminder(String token ,long noteId);
	public List<Note>  getAllNotes(String token);
	public List<Note> getArchiveNotes(String token);
	public List<Note> getTrashNotes(String token);
	public List<Note> getPinnedNotes(String token);
	public List<Note> getUnPinnedNotes(String token);
	public List<Note> sortByName(String token);
	public List<Note> sortByDate(String token);
	public Set<Note> getCollaboratedNotes(String token);
	public Set<User> getCollaboratedUser(String token ,long noteId);
	public String getRemainders(String token,long noteId);
	

}
