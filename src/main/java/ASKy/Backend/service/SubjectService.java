package ASKy.Backend.service;

import ASKy.Backend.dto.request.CreateSubjectRequest;
import ASKy.Backend.dto.response.SubjectResponse;
import ASKy.Backend.model.Subject;
import ASKy.Backend.model.Topic;
import ASKy.Backend.repository.SubjectRepository;
import ASKy.Backend.repository.TopicRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final ModelMapper modelMapper;
    private final TopicRepository topicRepository;

    public SubjectService(SubjectRepository subjectRepository, TopicRepository topicRepository ,ModelMapper modelMapper) {
        this.subjectRepository = subjectRepository;
        this.topicRepository = topicRepository;
        this.modelMapper = modelMapper;
    }

    public SubjectResponse createSubject(Integer topicId, CreateSubjectRequest request) {
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new EntityNotFoundException("Topic no encontrado"));

        Subject subject = modelMapper.map(request, Subject.class);
        subject.setTopic(topic);

        Subject savedSubject = subjectRepository.save(subject);

        // 🔹 Convertimos la entidad Subject a SubjectResponse
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
