package com.bridgeit.fundooapp.note.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.bridgeit.fundooapp.note.service.LabelService;
import com.bridgeit.fundooapp.note.dto.LabelDTO;
import com.bridgeit.fundooapp.note.dto.NotesDTO;
import com.bridgeit.fundooapp.note.model.Label;
import com.bridgeit.fundooapp.response.Response;

@RestController
@CrossOrigin(allowedHeaders = "*" ,origins = "*")
@RequestMapping("/user/labelstart")
public class LabelController 
{
	@Autowired
	private LabelService labelService;
	
	@PostMapping("/createlabel")
	ResponseEntity<Response> createLabel(@RequestBody LabelDTO labelDto , @RequestHeader String token) {
		Response statusResponse = labelService.createLabel(labelDto, token);
		return new ResponseEntity<Response>(statusResponse,HttpStatus.ACCEPTED);
	}
	
	
	@DeleteMapping("/deletelabel")
	ResponseEntity<Response> deleteLabel(@RequestHeader String token , @RequestParam long labelId) {
		Response statusResponse = labelService.deleteLabel(labelId, token);
		return new ResponseEntity<Response>(statusResponse,HttpStatus.OK);
	}
	
	
	@PutMapping("/updatelabel")
	ResponseEntity<Response> updateLabel(@RequestHeader String token , @RequestParam long labelId , @RequestBody LabelDTO labelDto){
		Response statusResponse = labelService.updateLabel(labelId, token, labelDto);
		return new ResponseEntity<Response>(statusResponse,HttpStatus.OK);
	}
	
	@GetMapping("/getthelabel")
	List<Label> getLabel(@RequestHeader String token){
		List<Label> listLabel = labelService.getAllLabel(token);
		return listLabel;
	}
	
	
	@PutMapping("/addlebeltothenote")
	ResponseEntity<Response> addNoteToLebel(@RequestParam long labelId , @RequestHeader String token , @RequestParam long noteId){
		Response statusResponse = labelService.addLabelToNote(labelId, token, noteId);
		return new ResponseEntity<Response>(statusResponse,HttpStatus.OK);
	}

	@GetMapping("/getlebelofthenote")
	List<Label> getLebelOfNote(@RequestHeader String token, @RequestParam long noteId){
		List<Label> listLabel = labelService.getLebelsOfNote(token, noteId);
		return listLabel;
	}
	
	
	@PutMapping("/removefromthenote")
	ResponseEntity<Response> removeFromNote(@RequestHeader String token, @RequestParam long noteId , @RequestParam long labelId){
		Response statusResponse = labelService.removeLabelFromNote(labelId, token, noteId);
		return new ResponseEntity<Response>(statusResponse,HttpStatus.OK);
	}
	
	@GetMapping("/getnotesofthelabel")
	List<NotesDTO> getNotesOfLabel(@RequestHeader String token , @RequestParam long labelId){
		List<NotesDTO> listNotes = labelService.getNotesOfLabel(token, labelId);
		return listNotes;
	}
	

}
