package ASKy.Backend.service;

import ASKy.Backend.dto.request.CreateSubjectRequest;
import ASKy.Backend.dto.response.SubjectResponse;
import ASKy.Backend.model.Subject;
import ASKy.Backend.repository.SubjectRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final ModelMapper modelMapper;

    public SubjectService(SubjectRepository subjectRepository, ModelMapper modelMapper) {
        this.subjectRepository = subjectRepository;
        this.modelMapper = modelMapper;
    }

    public SubjectResponse createSubject(CreateSubjectRequest request) {
        Subject subject = modelMapper.map(request, Subject.class);
        Subject savedSubject = subjectRepository.save(subject);
        return modelMapper.map(savedSubject, SubjectResponse.class);
    }

    public List<SubjectResponse> searchSubjects(String keyword) {
        List<Subject> subjects = subjectRepository.findByNameContainingOrDescriptionContaining(keyword, keyword);
        return subjects.stream()
                .map(subject -> modelMapper.map(subject, SubjectResponse.class))
                .toList();
    }

    public void deleteSubject(Integer subjectId) {
        if (!subjectRepository.existsById(subjectId)) {
            throw new EntityNotFoundException("Subject no encontrado");
        }
        subjectRepository.deleteById(subjectId);
    }

    public List<SubjectResponse> getAllSubjects() {
        return subjectRepository.findAll().stream()
                .map(subject -> modelMapper.map(subject, SubjectResponse.class))
                .toList();
    }
}
