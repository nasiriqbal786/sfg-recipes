package com.springframework.sfgrecipes.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.springframework.sfgrecipes.commands.NotesCommand;
import com.springframework.sfgrecipes.model.Notes;

@Component
public class NotesToNotesCommand implements Converter<Notes, NotesCommand> {

	@Override
	@Nullable
	public NotesCommand convert(Notes source) {
		if(source == null) {
			return null;
		}
		NotesCommand notes = new NotesCommand();
		notes.setId(source.getId());
		notes.setRecipeNotes(source.getRecipeNotes());
		return notes;
	}

}
