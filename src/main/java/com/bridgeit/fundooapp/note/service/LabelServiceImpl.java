package com.bridgeit.fundooapp.note.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.bridgeit.fundooapp.exception.LabelException;
import com.bridgeit.fundooapp.exception.NoteException;
import com.bridgeit.fundooapp.note.dto.LabelDTO;
import com.bridgeit.fundooapp.note.dto.NotesDTO;
import com.bridgeit.fundooapp.note.model.Label;
import com.bridgeit.fundooapp.note.model.Note;
import com.bridgeit.fundooapp.note.repository.NoteRepository;
import com.bridgeit.fundooapp.note.repository.LabelRepository;
import com.bridgeit.fundooapp.note.service.LabelService;
import com.bridgeit.fundooapp.response.Response;
import com.bridgeit.fundooapp.user.model.User;
import com.bridgeit.fundooapp.user.repository.UserRepository;
import com.bridgeit.fundooapp.util.StatusHelper;
import com.bridgeit.fundooapp.util.UserToken;

@Service("labelService")
@PropertySource("classpath:message.properties")
public class LabelServiceImpl implements LabelService
{

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private LabelRepository labelRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private NoteRepository notesRepository;
	
	@Autowired
	private UserToken userToken;
	
	@Autowired
	private Environment environment;
	

	
	@Override
	public Response createLabel(LabelDTO labelDto, String token) {
		long userId = userToken.tokenVerify(token);
		Optional<User> user = userRepository.findById(userId);
		if(!user.isPresent()) {
			throw new LabelException("Invalid input", -6);
		}
		if(labelDto.getLabelName().isEmpty()) {
			throw new LabelException("Label has no name", -6);
		}
		Optional<Label> labelAvailability = labelRepository.findByUserIdAndLabelName(userId, labelDto.getLabelName());
		if(labelAvailability.isPresent()) {
			throw new LabelException("Label already exist", -6);
		}
		
		Label label = modelMapper.map(labelDto,Label.class);
		
		label.setLabelName(labelDto.getLabelName());
		label.setUserId(userId);
		label.setCreatedDate(LocalDateTime.now());
		label.setModifiedDate(LocalDateTime.now());
		labelRepository.save(label);
		user.get().getLabel().add(label);
		userRepository.save(user.get());
		Response response = StatusHelper.statusInfo(environment.getProperty("status.label.created"), Integer.parseInt(environment.getProperty("status.success.code")));
		return response;
	}


	@Override
	public Response deleteLabel(long labelId, String token) {
		long userId = userToken.tokenVerify(token);
		Optional<User> user = userRepository.findById(userId);
		if(!user.isPresent()) {
			throw new LabelException("Invalid input", -6);
		}
		Label label = labelRepository.findByLabelIdAndUserId(labelId, userId);
		if(label == null) {
			throw new LabelException("Invalid input", -6);
		}
		labelRepository.delete(label);
		Response response = StatusHelper.statusInfo(environment.getProperty("status.label.deleted"), Integer.parseInt(environment.getProperty("status.success.code")));
		return response;

	}

	
	@Override
	public Response updateLabel(long labelId , String token ,LabelDTO labelDto) {
		long userId = userToken.tokenVerify(token);
		Optional<User> user = userRepository.findById(userId);
		if(!user.isPresent()) {
			throw new LabelException("Invalid input", -6);
		}
		Label label = labelRepository.findByLabelIdAndUserId(labelId, userId);
		if(label == null ) {
			throw new LabelException("No label exist", -6);
		}
		if(labelDto.getLabelName().isEmpty()) {
			throw new LabelException("Label has no name", -6);
		}
		Optional<Label> labelAvailability = labelRepository.findByUserIdAndLabelName(userId, labelDto.getLabelName());
		if(labelAvailability.isPresent()) {
			throw new LabelException("Label already exist", -6);
		}
		label.setLabelName(labelDto.getLabelName());
		label.setModifiedDate(LocalDateTime.now());
		labelRepository.save(label);
		Response response = StatusHelper.statusInfo(environment.getProperty("status.label.updated"), Integer.parseInt(environment.getProperty("status.success.code")));
		return response;
	}


	@Override
	public List<Label> getAllLabel(String token) {
		long userId = userToken.tokenVerify(token);
		Optional<User> user = userRepository.findById(userId);
		if(!user.isPresent()) {
			throw new LabelException("Invalid input", -6);
		}
//		List<Label> labels = user.get().getLabel();
		
		List<Label> labels = labelRepository.findByUserId(userId);
		List<Label> listLabel = new ArrayList<>();
		for(Label noteLabel : labels) {
			listLabel.add(noteLabel);
		}
		System.out.println(labels);
		return listLabel;
	}


	@Override
	public Response addLabelToNote(long labelId, String token , long noteId) {
		long userId = userToken.tokenVerify(token);
		Optional<User> user = userRepository.findById(userId);
		if(!user.isPresent()) {
			throw new LabelException("Invalid input", -6);
		}
		Label label = labelRepository.findByLabelIdAndUserId(labelId, userId);
		if(label == null) {
			throw new LabelException("No such lebel exist", -6);
		}
		Note note =  notesRepository.findByIdAndUserId(noteId, userId);
		if(note == null) {
			throw new LabelException("No such note exist", -6);
		}
		if(note.getListLabel().contains(label)) {
			throw new LabelException("Label already exist", -6);
		}
//		label.setModifiedDate(LocalDateTime.now());
//		label.getNotes().add(note);
		note.getListLabel().add(label);
		note.setModified(LocalDateTime.now());
//		labelRepository.save(label);
		notesRepository.save(note);
		Response response = StatusHelper.statusInfo(environment.getProperty("status.label.addedtonote"), Integer.parseInt(environment.getProperty("status.success.code")));
		return response;
	}

	

	@Override
	public Response removeLabelFromNote(long labelId ,String token , long noteId) {
		long userId = userToken.tokenVerify(token);
		Optional<User> user = userRepository.findById(userId);
		if(!user.isPresent()) {
			throw new LabelException("Invalid input", -6);
		}
		Label label = labelRepository.findByLabelIdAndUserId(labelId , userId);
		if(label == null) {
			throw new LabelException("No such lebel exist", -6);
		}
		Note note =  notesRepository.findByIdAndUserId(noteId, userId);
		if(note == null) {
			throw new LabelException("No such note exist", -6);
		}
		label.setModifiedDate(LocalDateTime.now());
		note.getListLabel().remove(label);
		note.setModified(LocalDateTime.now());
		labelRepository.save(label);
		notesRepository.save(note);
		Response response = StatusHelper.statusInfo(environment.getProperty("status.label.removedfromnote"), Integer.parseInt(environment.getProperty("status.success.code")));
		return response;
	}


	@Override
	public List<Label> getLebelsOfNote(String token, long noteId) {
		long userId = userToken.tokenVerify(token);
		Optional<User> user = userRepository.findById(userId);
		if(!user.isPresent()) {
			throw new LabelException("User does not exist", -6);
		}
		Optional<Note> note = notesRepository.findById(noteId);
		if(!note.isPresent()) {
			throw new NoteException("Note does not exist", -6);
		}
		List<Label> lebel = note.get().getListLabel();
		
		return lebel;
		
	}
	
	
	@Override
	public List<NotesDTO> getNotesOfLabel(String token, long labelId) {
//		long userId = userToken.tokenVerify(token);
//		Optional<User> user = userRepository.findById(userId);
//		if(!user.isPresent()) {
//			throw new TokenException("Invalid input", -6);
//		}
//		Optional<Label> label = labelRepository.findById(labelId);
//		if(!label.isPresent()) {
//			throw new LabelException("No lebel exist", -6);
//		}
//		List<Note> notes = label.get().getNotes();
//		List<NotesDto> listNotes = new ArrayList<>();
//		for (Note usernotes : notes) {
//			NotesDto noteDto = modelMapper.map(usernotes, NotesDto.class);
//			listNotes.add(noteDto);
//		}
//		return listNotes;
		return null;
	}
}
	
	
