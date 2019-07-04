package com.bridgeit.fundooapp.note.controller;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.fundooapp.note.service.NoteService;
import com.bridgeit.fundooapp.note.dto.NotesDTO;
import com.bridgeit.fundooapp.response.Response;
import com.bridgeit.fundooapp.user.model.User;
import com.bridgeit.fundooapp.note.model.Note;


@RestController
@CrossOrigin(allowedHeaders = "*" ,origins = "*")
@RequestMapping("/user/notestart")
//annotation for set environment file 
@PropertySource("classpath:message.properties")
public class NotesController 
{
	@Autowired
	private NoteService noteService;
	
	@PostMapping("/createnote")
	public ResponseEntity<Response> creatingNote(HttpServletRequest request , @RequestBody NotesDTO notesDto , @RequestHeader("token") String token)
	{
		Response responseStatus = noteService.createNote(notesDto, token);
		return new ResponseEntity<Response>(responseStatus,HttpStatus.OK);
	}
	
	@PutMapping("/updatenote")
	public ResponseEntity<Response> updatingNote(@RequestBody NotesDTO notesDto , @RequestHeader String token , @RequestParam long noteId){
		
		Response responseStatus = noteService.updateNote(notesDto, token , noteId);
		return new ResponseEntity<Response> (responseStatus,HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/deletenote")
	public ResponseEntity<Response> deletingNote(@RequestHeader String token ,@RequestParam long noteId){
		Response responseStatus = noteService.delete(token, noteId);
		return new ResponseEntity<Response> (responseStatus,HttpStatus.OK);
	}
	
	@GetMapping("/getallthenotes")
	public List<Note>  getAllNotes(@RequestHeader String token) {
		List<Note> listnotes = noteService.getAllNotes(token);
		return listnotes;
	}
	
	@PutMapping("/pinnotes")
	public ResponseEntity<Response> pinNote(@RequestHeader String token , @RequestParam long noteId){
		Response responseStatus = noteService.pinAndUnPin(token, noteId);
		return new ResponseEntity<Response> (responseStatus,HttpStatus.OK);
	}
	
	
		@PutMapping("/archivenotes")
		public ResponseEntity<Response> archiveNote(@RequestHeader String token , @RequestParam long noteId){
			Response responseStatus = noteService.archiveAndUnArchive(token, noteId);
			return new ResponseEntity<Response> (responseStatus,HttpStatus.OK);
		}
		
	
			@PutMapping("/trashnotes")
			public ResponseEntity<Response> trashNote(@RequestHeader String token, @RequestParam long noteId){
				Response responseStatus = noteService.trashAndUnTrash(token, noteId);
				return new ResponseEntity<Response> (responseStatus,HttpStatus.OK);
			}
			
			@DeleteMapping("/deletepermanentnotes")
			public ResponseEntity<Response> deleteNote(@RequestHeader String token, @RequestParam long noteId){
				Response responseStatus = noteService.deletePermanently(token, noteId);
				return new ResponseEntity<Response> (responseStatus,HttpStatus.OK);
			}
			
			@GetMapping("/getallarchivenotes")
			public List<Note>  getArchiveNotes(@RequestHeader String token) {
				List<Note> listnotes = noteService.getArchiveNotes(token);
				return listnotes;
			}
			
			@GetMapping("/getalltrashnotes")
			public List<Note>  getTrashNotes(@RequestHeader String token) {
				List<Note> listnotes = noteService.getTrashNotes(token);
				return listnotes;
			}
			
			
			@GetMapping("/getallpinnednotes")
			public List<Note> getPinnedNotes(@RequestHeader String token){
				List<Note> listnotes = noteService.getPinnedNotes(token);
				return listnotes;
			}
			
			
			@GetMapping("/getallunpinnednotes")
			public List<Note> getUnPinnedNotes(@RequestHeader String token){
				List<Note> listnotes = noteService.getUnPinnedNotes(token);
				return listnotes;
			}
			
			@PutMapping("/color")
			public ResponseEntity<Response> changeColor(@RequestHeader String token,@RequestParam long noteId,@RequestParam String colorCode) {
				Response responseStatus = noteService.setColor(token, colorCode, noteId);
				return new  ResponseEntity<Response> (responseStatus,HttpStatus.OK);
			}	
			
			@PutMapping("/addcollaborator")
			public  ResponseEntity<Response> addCollab(@RequestHeader String token,@RequestParam String email,@RequestParam long noteId) {
				Response responseStatus = noteService.addCollaborator(token, email, noteId);
				return new  ResponseEntity<Response> (responseStatus,HttpStatus.OK);
			}
			
			@PutMapping("/removecollaborator")
			public ResponseEntity<Response> removeCollab(@RequestHeader String token,@RequestParam String email,@RequestParam long noteId) {
				Response responseStatus = noteService.removeCollaborator(token, email, noteId);
				return new  ResponseEntity<Response> (responseStatus,HttpStatus.OK);
			}
		
			@GetMapping("/getallcollaboratednotes")
			public Set<Note> getCollaboratedNotes(@RequestHeader String token) {
				Set<Note> collaboratednotes = noteService.getCollaboratedNotes(token);
				return collaboratednotes;
			}
			
			@GetMapping("/getallcollaborateduser")
			public Set<User> getCollaboratedUser(@RequestHeader String token , @RequestParam long noteId) {
				Set<User> collaboratedUser = noteService.getCollaboratedUser(token, noteId);
				return collaboratedUser;
			}
			
			@PutMapping("/addreminder")
			public ResponseEntity<Response> addingReminder(@RequestHeader String token , @RequestParam long noteId , @RequestParam String reminder) {
				Response responseStatus = noteService.addReminder(token, noteId, reminder);
				return new  ResponseEntity<Response> (responseStatus,HttpStatus.OK);
			}
			@PutMapping("/removereminder")
			public ResponseEntity<Response> removingReminder(@RequestHeader String token , @RequestParam long noteId) { 
				Response responseStatus = noteService.removeReminder(token, noteId);
				return new  ResponseEntity<Response> (responseStatus,HttpStatus.OK);
			}
			
			@GetMapping("/getremainders")
			public ResponseEntity<String> getRemainder(@RequestHeader String token , @RequestParam long noteId) {
				String responseStatus = noteService.getRemainders(token, noteId);
				return new  ResponseEntity<String> (responseStatus,HttpStatus.OK);
			}
			
			
			@GetMapping("/sortbytitle")
			public List<Note> sortByTitle(@RequestHeader String token) {
				List<Note> sortedNoteByTitle = noteService.sortByName(token);
				return sortedNoteByTitle;
			}
			
			@GetMapping("/sortbydate")
			public List<Note> sortByDate(@RequestHeader String token) {
				List<Note> sortedByDate = noteService.sortByDate(token);
				return sortedByDate;
		}
}
