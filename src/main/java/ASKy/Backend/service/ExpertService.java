package ASKy.Backend.service;

import ASKy.Backend.dto.request.ExpertFilterRequest;
import ASKy.Backend.dto.response.ExpertResponse;
import ASKy.Backend.model.User;
import ASKy.Backend.repository.ExpertRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpertService {

    private final ExpertRepository expertRepository;
    private final ModelMapper modelMapper;

    public ExpertService(ExpertRepository expertRepository, ModelMapper modelMapper) {
        this.expertRepository = expertRepository;
        this.modelMapper = modelMapper;
    }

    public List<ExpertResponse> getExperts(ExpertFilterRequest filter) {
        List<User> experts = expertRepository.findExperts(
                filter.getSpecialty(),
                filter.getName(),
                filter.getOrderBy()
        );

        return experts.stream()
                .map(expert -> modelMapper.map(expert, ExpertResponse.class))
                .toList();
    }
}
