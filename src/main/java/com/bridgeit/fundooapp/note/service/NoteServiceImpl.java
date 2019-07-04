package com.bridgeit.fundooapp.note.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.bridgeit.fundooapp.note.repository.NoteRepository;
import com.bridgeit.fundooapp.user.repository.UserRepository;
import com.bridgeit.fundooapp.util.UserToken;
import com.bridgeit.fundooapp.exception.NoteException;
import com.bridgeit.fundooapp.exception.EmailException;
import com.bridgeit.fundooapp.note.dto.NotesDTO;
import com.bridgeit.fundooapp.user.model.Email;
import com.bridgeit.fundooapp.note.model.Note;
import com.bridgeit.fundooapp.user.model.User;
import com.bridgeit.fundooapp.util.StatusHelper;
import com.bridgeit.fundooapp.response.Response;

@Service("notesService")
@PropertySource("classpath:message.properties")

public class NoteServiceImpl implements NoteService
{

	@Autowired
	private UserToken userToken;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private NoteRepository notesRepository;
	
	@Autowired
	private Environment environment;
	
	@Override
	public Response createNote(NotesDTO notesDto, String token) 
	{
		if(notesDto.getTitle().isEmpty() && notesDto.getDescription().isEmpty()) {
			
			throw new NoteException("Title and description are empty", -5);

		}
		
		long id = userToken.tokenVerify(token);
		Note notes = modelMapper.map(notesDto, Note.class);
		Optional<User> user = userRepository.findById(id);
		notes.setUserId(id);
		notes.setCreated(LocalDateTime.now());
		notes.setModified(LocalDateTime.now());
		user.get().getNotes().add(notes);
		notesRepository.save(notes);
		userRepository.save(user.get());
	
		Response response = StatusHelper.statusInfo(environment.getProperty("status.notes.createdSuccessfull"), Integer.parseInt(environment.getProperty("status.success.code")));
		return response;
		
	}
	
	@Override
	public Response updateNote(NotesDTO notesDto , String token , long noteId) {
		if(notesDto.getTitle().isEmpty() && notesDto.getDescription().isEmpty()) {
			throw new NoteException("Title and description are empty", -5);
		}
		
		long id = userToken.tokenVerify(token);
		Note notes = notesRepository.findByIdAndUserId(noteId, id);
		notes.setTitle(notesDto.getTitle());
		notes.setDescription(notesDto.getDescription());
		notes.setColorCode(notesDto.getColor());
		notes.setModified(LocalDateTime.now());
		notesRepository.save(notes);
		
		Response response = StatusHelper.statusInfo(environment.getProperty("status.notes.updated"),Integer.parseInt(environment.getProperty("status.success.code")));
		return response;
	}

	@Override
	public Response delete(String token, long noteId) {
		long id = userToken.tokenVerify(token);
		Note notes = notesRepository.findByIdAndUserId(noteId, id);
		if(notes == null) {
			throw new NoteException("Invalid input", -5);
		}
		if(notes.isTrash() == false) {
			notes.setTrash(true);
			notes.setModified(LocalDateTime.now());
			notesRepository.save(notes);
			Response response = StatusHelper.statusInfo(environment.getProperty("status.note.trashed"),Integer.parseInt(environment.getProperty("status.success.code")));
			return response;
		}
		Response response = StatusHelper.statusInfo(environment.getProperty("status.note.trashError"),Integer.parseInt(environment.getProperty("status.note.errorCode")));
		return response;
	}

	
	@Override
	public List<Note> getAllNotes(String token) {
		long id = userToken.tokenVerify(token);
		List<Note> notes = (List<Note>) notesRepository.findByUserId(id);
	return notes;
	}

	
	@Override
	public Response pinAndUnPin(String token, long noteId) {
		long id = userToken.tokenVerify(token);
		Note notes = notesRepository.findByIdAndUserId(noteId, id);
		if(notes == null) {
			throw new NoteException("Invalid input", -5);
		}
		if(notes.isPin() == false) {
			notes.setPin(true);
			notesRepository.save(notes);
			Response response = StatusHelper.statusInfo(environment.getProperty("status.note.pinned"),Integer.parseInt(environment.getProperty("status.success.code")));
			return response;
		}
		else {
			notes.setPin(false);
			notesRepository.save(notes);
			Response response = StatusHelper.statusInfo(environment.getProperty("status.note.unpinned"),Integer.parseInt(environment.getProperty("status.success.code")));
			return response;
		}
	}
	
	@Override
	public Response archiveAndUnArchive(String token, long noteId) {
		long id = userToken.tokenVerify(token);
		Note notes = notesRepository.findByIdAndUserId(noteId, id);
		if(notes == null) {
			throw new NoteException("Invalid input", -5);
		}
		if(notes.isArchive() == false) {
			notes.setArchive(true);
			notesRepository.save(notes);
			Response response = StatusHelper.statusInfo(environment.getProperty("status.note.archieved"),Integer.parseInt(environment.getProperty("status.success.code")));
			return response;
		}
		else {
			notes.setArchive(false);
			notesRepository.save(notes);
			Response response = StatusHelper.statusInfo(environment.getProperty("status.note.unarchieved"),Integer.parseInt(environment.getProperty("status.success.code")));
			return response;
		}
	}

	
	@Override
	public Response trashAndUnTrash(String token, long noteId) {
		long id = userToken.tokenVerify(token);
		Note notes = notesRepository.findByIdAndUserId(noteId, id);
		if(notes.isTrash() == false) {
			notes.setTrash(true);
			notesRepository.save(notes);
			Response response = StatusHelper.statusInfo(environment.getProperty("status.note.trashed"),Integer.parseInt(environment.getProperty("status.success.code")));
			return response;
		}
		else {
			notes.setTrash(false);
			notesRepository.save(notes);
			Response response = StatusHelper.statusInfo(environment.getProperty("status.note.untrashed"),Integer.parseInt(environment.getProperty("status.success.code")));
			return response;
		}
	}
	
	@Override
	public Response deletePermanently(String token, long noteId) {
		long id = userToken.tokenVerify(token);
		Note notes = notesRepository.findByIdAndUserId(noteId, id);
		if(notes.isTrash() == true) {
			notesRepository.delete(notes);
			Response response = StatusHelper.statusInfo(environment.getProperty("status.note.deleted"),Integer.parseInt(environment.getProperty("status.success.code")));
			return response;
		}else {
			Response response = StatusHelper.statusInfo(environment.getProperty("status.note.notdeleted"),Integer.parseInt(environment.getProperty("status.note.errorCode")));
			return response;
		}
	}

	
	@Override
	public List<Note> getArchiveNotes(String token) {
		long id = userToken.tokenVerify(token);
		List<Note> notes = (List<Note>) notesRepository.findByUserId(id);
		List<Note> listNotes = new ArrayList<>();
		for(Note userNotes : notes) {
			//NotesDto notesDto = modelMapper.map(userNotes, NotesDto.class);
			if(userNotes.isArchive() == true) {
				listNotes.add(userNotes);
			}
		}
		return listNotes;
	}

	
	@Override
	public List<Note> getTrashNotes(String token) {
		long id = userToken.tokenVerify(token);
		List<Note> notes = (List<Note>) notesRepository.findByUserId(id);
		List<Note> listNotes = new ArrayList<>();
		for(Note userNotes : notes) {
			if(userNotes.isTrash() == true) {
				listNotes.add(userNotes);
			}
		}
		return listNotes;
	}
	
	
	@Override
	public List<Note> getPinnedNotes(String token) {
		long id = userToken.tokenVerify(token);
		List<Note> notes = (List<Note>) notesRepository.findByUserId(id);
		List<Note> listNotes = new ArrayList<>();
		for(Note userNotes : notes) {
			if(userNotes.isPin() == true && userNotes.isArchive() == false && userNotes.isTrash() == false) {
				listNotes.add(userNotes);
			}
		}
		return listNotes;
	}

	
	@Override
	public List<Note> getUnPinnedNotes(String token) {
		long id = userToken.tokenVerify(token);
		List<Note> notes = (List<Note>) notesRepository.findByUserId(id);
		List<Note> listNotes = new ArrayList<>();
		for(Note userNotes : notes) {
			if(userNotes.isPin() == false && userNotes.isArchive() == false && userNotes.isTrash() == false) {
				listNotes.add(userNotes);
			}
		}
		return listNotes;
	}

	@Override
	public Response setColor(String token, String colorCode , long noteId) {
		long uderId = userToken.tokenVerify(token);
		Note note = notesRepository.findByIdAndUserId(noteId, uderId);
		note.setColorCode(colorCode);
		note.setModified(LocalDateTime.now());
		notesRepository.save(note);
		Response response = StatusHelper.statusInfo(environment.getProperty("status.note.color"),Integer.parseInt(environment.getProperty("status.success.code")));
		return response;
	}
	
	@Override
	public Response addCollaborator(String token, String email, long noteId) {
		Email collabEmail = new Email();
		long userId = userToken.tokenVerify(token);
		
		Optional<User> owner = userRepository.findById(userId);
		Optional<User> user = userRepository.findByEmail(email);
		
		if(!user.isPresent())
			throw new EmailException("No user exist", -4);
		
		Note note = notesRepository.findByIdAndUserId(noteId, userId);
		
		if(note == null)
			throw new NoteException("No note exist", -5);
		
		if(user.get().getCollaboratedNotes().contains(note)) 
			throw new NoteException("Note is already collaborated", -5);
		
		user.get().getCollaboratedNotes().add(note);
		note.getCollaboratedUser().add(user.get());
		
		userRepository.save(user.get());
		notesRepository.save(note);
		
		collabEmail.setFrom("nehapalekar026@gmail.com");
		collabEmail.setTo(email);
		collabEmail.setSubject("Note collaboration ");
		collabEmail.setBody("Note from " + owner.get().getEmail() + " collaborated to you\nTitle : " + note.getTitle() +"\nDescription : " + note.getDescription());
		
		
		Response response = StatusHelper.statusInfo(environment.getProperty("status.collab.success"),Integer.parseInt(environment.getProperty("status.success.code")));
		return response;
	}
	
	@Override
	public Response removeCollaborator(String token, String email, long noteId) {
		
		long userId = userToken.tokenVerify(token);
		Optional<User> user = userRepository.findByEmail(email);
		
		if(!user.isPresent()) 
			throw new EmailException("No user exist", -4);
		
		Note note = notesRepository.findByIdAndUserId(noteId, userId);
		
		if(note == null)
			throw new NoteException("No note exist", -5);
		
		user.get().getCollaboratedNotes().remove(note);
		note.getCollaboratedUser().remove(user.get());
		
		userRepository.save(user.get());
		notesRepository.save(note);
		
		Response response = StatusHelper.statusInfo(environment.getProperty("status.collab.remove"),Integer.parseInt(environment.getProperty("status.success.code")));
		return response;
	}

	@Override
	public Set<Note> getCollaboratedNotes(String token) {
		
		long userId = userToken.tokenVerify(token);
		Optional<User> user = userRepository.findById(userId);
		
		if(!user.isPresent())
			throw new NoteException("No user exist", -5);	
		
		Set<Note> collaboratedNotes = user.get().getCollaboratedNotes();
		
		return collaboratedNotes;
	}
	
	@Override
	public Set<User> getCollaboratedUser(String token,long noteId) {
		long userId = userToken.tokenVerify(token);
		Optional<User> user = userRepository.findById(userId);
		if(!user.isPresent())
			throw new NoteException("No user exist", -5);	
		Optional<Note> note = notesRepository.findById(noteId);
		Set<User> collaboratedUser = note.get().getCollaboratedUser();
		return collaboratedUser;
	}
	
	

	@Override
	public Response addReminder(String token, long noteId, String time) {
		long userId = userToken.tokenVerify(token);
		Optional<User> user = userRepository.findById(userId);
		if(!user.isPresent())
			throw new NoteException("No user exist", -5);	
		Optional<Note> note = notesRepository.findById(noteId);
		note.get().setReminder(time);
		notesRepository.save(note.get());
		Response response = StatusHelper.statusInfo(environment.getProperty("status.reminder.added"),Integer.parseInt(environment.getProperty("status.success.code")));
		return response;
	}

	@Override
	public Response removeReminder(String token, long noteId) {
		long userId = userToken.tokenVerify(token);
		Optional<User> user = userRepository.findById(userId);
		if(!user.isPresent())
			throw new NoteException("No user exist", -5);	
		Optional<Note> note = notesRepository.findById(noteId);
		note.get().setReminder(null);
		notesRepository.save(note.get());
		Response response = StatusHelper.statusInfo(environment.getProperty("status.reminder.removed"),Integer.parseInt(environment.getProperty("status.success.code")));
		return response;
	}
	

	@Override
	public String getRemainders(String token, long noteId) {
		long userId = userToken.tokenVerify(token);
		Optional<User> user = userRepository.findById(userId);
		if(!user.isPresent())
			throw new NoteException("No user exist", -5);	
		Optional<Note> note = notesRepository.findById(noteId);
		String remainder = note.get().getReminder();
		return remainder;
	}
	

	@Override
	public List<Note> sortByDate(String token) {
		long userId  = userToken.tokenVerify(token);
		Optional<User> optUser = userRepository.findById(userId);
		if (optUser.isPresent()) {
			
			List<Note> noteList = notesRepository.findAll();
			for (int i = 0; i < noteList.size(); i++) {
				for (int j = 0; j < noteList.size(); j++) {
					if (noteList.get(i).getCreated().compareTo(noteList.get(i).getCreated()) < 0) {
						Note note = noteList.get(i);
						noteList.set(i, noteList.get(j));
						noteList.set(j, note);
					}
				}
			}
			return noteList;
		} else {
			throw new NoteException("No user exist", -5);	
		}
	}


	@Override
	public List<Note> sortByName(String token) {
		long userId  = userToken.tokenVerify(token);
		Optional<User> optUser = userRepository.findById(userId);
		if (optUser.isPresent()) {
			
			List<Note> noteList = notesRepository.findAll();
			
			return noteList.stream().sorted().collect(Collectors.toList());
			
			
		} else {
			throw new NoteException("No user exist", -5);	
		}
	}
	
}

