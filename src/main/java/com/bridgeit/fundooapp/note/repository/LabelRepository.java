package com.bridgeit.fundooapp.note.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bridgeit.fundooapp.note.model.Label;

public interface LabelRepository extends JpaRepository<Label, Long>
{
	 
	public Label findByLabelIdAndUserId(long labelId , long userId);
	public List<Label> findByUserId(long userId);
	public Optional<Label> findByUserIdAndLabelName(long userId , String labelName);

}
