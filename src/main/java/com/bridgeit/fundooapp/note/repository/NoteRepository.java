package com.bridgeit.fundooapp.note.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bridgeit.fundooapp.note.model.Note;

public interface NoteRepository extends JpaRepository<Note, Long> 
{
	public Note findByIdAndUserId(long id , long userId);
	public List<Note> findByUserId(long id);

}
