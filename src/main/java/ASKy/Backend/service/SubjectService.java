package ASKy.Backend.service;

import ASKy.Backend.dto.request.CreateSubjectRequest;
import ASKy.Backend.dto.response.SubjectResponse;
import ASKy.Backend.model.Subject;
import ASKy.Backend.model.Topic;
import ASKy.Backend.repository.ISubjectRepository;
import ASKy.Backend.repository.ITopicRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {

    private final ISubjectRepository ISubjectRepository;
    private final ModelMapper modelMapper;
    private final ITopicRepository ITopicRepository;

    public SubjectService(ISubjectRepository ISubjectRepository, ITopicRepository ITopicRepository, ModelMapper modelMapper) {
        this.ISubjectRepository = ISubjectRepository;
        this.ITopicRepository = ITopicRepository;
        this.modelMapper = modelMapper;
    }

    public SubjectResponse createSubject(Integer topicId, CreateSubjectRequest request) {
        Topic topic = ITopicRepository.findById(topicId)
                .orElseThrow(() -> new EntityNotFoundException("Topic no encontrado"));

        Subject subject = modelMapper.map(request, Subject.class);
        subject.setTopic(topic);

        Subject savedSubject = ISubjectRepository.save(subject);

        // 🔹 Convertimos la entidad Subject a SubjectResponse
        return modelMapper.map(savedSubject, SubjectResponse.class);
    }

    public List<SubjectResponse> searchSubjects(String keyword) {
        List<Subject> subjects = ISubjectRepository.findByNameContainingOrDescriptionContaining(keyword, keyword);
        return subjects.stream()
                .map(subject -> modelMapper.map(subject, SubjectResponse.class))
                .toList();
    }

    public void deleteSubject(Integer subjectId) {
        if (!ISubjectRepository.existsById(subjectId)) {
            throw new EntityNotFoundException("Subject no encontrado");
        }
        ISubjectRepository.deleteById(subjectId);
    }

    public List<SubjectResponse> getAllSubjects() {
        return ISubjectRepository.findAll().stream()
                .map(subject -> modelMapper.map(subject, SubjectResponse.class))
                .toList();
    }
}
