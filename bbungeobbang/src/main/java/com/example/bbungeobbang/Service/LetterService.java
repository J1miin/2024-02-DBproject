package com.example.bbungeobbang.Service;

import com.example.bbungeobbang.DTO.LetterDTO;
import com.example.bbungeobbang.Entity.Letter;
import com.example.bbungeobbang.Repository.LetterRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LetterService {
    private final LetterRepository letterRepository;
    public LetterService(LetterRepository letterRepository) {
        this.letterRepository = letterRepository;
    }
    public List<LetterDTO> getAllLetters(){
        List<Letter> letters = letterRepository.findAll();

        return letters.stream().map(letter->{
            LetterDTO letterDTO = new LetterDTO();
            letterDTO.setLetterId(letter.getLetterId());
            letterDTO.setContents(letter.getContents());
            letterDTO.setCreateDate(letter.getCreateDate());
            letterDTO.setIsBaked(letter.getIsBaked());
            return letterDTO;
        }).collect(Collectors.toList());
    }
}
