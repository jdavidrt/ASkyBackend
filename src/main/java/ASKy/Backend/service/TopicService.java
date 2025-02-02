package ASKy.Backend.service;

import ASKy.Backend.dto.request.CreateTopicRequest;
import ASKy.Backend.dto.response.TopicResponse;
import ASKy.Backend.model.Subject;
import ASKy.Backend.model.Topic;
import ASKy.Backend.repository.SubjectRepository;
import ASKy.Backend.repository.TopicRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicService {

    private final TopicRepository topicRepository;
    private final SubjectRepository subjectRepository;
    private final ModelMapper modelMapper;

    public TopicService(TopicRepository topicRepository, SubjectRepository subjectRepository, ModelMapper modelMapper) {
        this.topicRepository = topicRepository;
        this.subjectRepository = subjectRepository;
        this.modelMapper = modelMapper;
    }

    public TopicResponse createTopic(CreateTopicRequest request) {
        Topic topic = modelMapper.map(request, Topic.class);
        Topic savedTopic = topicRepository.save(topic);
        return modelMapper.map(savedTopic, TopicResponse.class);
    }

    public List<TopicResponse> getTopicsBySubject(Integer subjectId) {
        List<Topic> topics = topicRepository.findBySubtopicsSubjectId(subjectId);
        return topics.stream()
                .map(topic -> modelMapper.map(topic, TopicResponse.class))
                .toList();
    }

    public List<TopicResponse> searchTopics(String keyword) {
        List<Topic> topics = topicRepository.findByNameContainingOrDescriptionContaining(keyword, keyword);
        return topics.stream()
                .map(topic -> modelMapper.map(topic, TopicResponse.class))
                .toList();
    }

    public void deleteTopic(Integer topicId) {
        if (!topicRepository.existsById(topicId)) {
            throw new EntityNotFoundException("Tema no encontrado");
        }
        topicRepository.deleteById(topicId);
    }

    public List<TopicResponse> getAllTopics() {
        return topicRepository.findAll().stream()
                .map(topic -> modelMapper.map(topic, TopicResponse.class))
                .toList();
    }
}
