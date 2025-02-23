package ASKy.Backend.service;

import ASKy.Backend.dto.request.CreateTopicRequest;
import ASKy.Backend.dto.response.TopicResponse;
import ASKy.Backend.model.Topic;
import ASKy.Backend.repository.ISubjectRepository;
import ASKy.Backend.repository.ITopicRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicService {

    private final ITopicRepository ITopicRepository;
    private final ISubjectRepository ISubjectRepository;
    private final ModelMapper modelMapper;

    public TopicService(ITopicRepository ITopicRepository, ISubjectRepository ISubjectRepository, ModelMapper modelMapper) {
        this.ITopicRepository = ITopicRepository;
        this.ISubjectRepository = ISubjectRepository;
        this.modelMapper = modelMapper;
    }

    public TopicResponse createTopic(CreateTopicRequest request) {
        Topic topic = modelMapper.map(request, Topic.class);
        Topic savedTopic = ITopicRepository.save(topic);
        return modelMapper.map(savedTopic, TopicResponse.class);
    }

    public List<TopicResponse> getTopicsBySubject(Integer subjectId) {
        List<Topic> topics = ITopicRepository.findBySubtopicsSubjectId(subjectId);
        return topics.stream()
                .map(topic -> modelMapper.map(topic, TopicResponse.class))
                .toList();
    }

    public List<TopicResponse> searchTopics(String keyword) {
        List<Topic> topics = ITopicRepository.findByNameContainingOrDescriptionContaining(keyword, keyword);
        return topics.stream()
                .map(topic -> modelMapper.map(topic, TopicResponse.class))
                .toList();
    }

    public void deleteTopic(Integer topicId) {
        if (!ITopicRepository.existsById(topicId)) {
            throw new EntityNotFoundException("Tema no encontrado");
        }
        ITopicRepository.deleteById(topicId);
    }

    public List<TopicResponse> getAllTopics() {
        return ITopicRepository.findAll().stream()
                .map(topic -> modelMapper.map(topic, TopicResponse.class))
                .toList();
    }
}
