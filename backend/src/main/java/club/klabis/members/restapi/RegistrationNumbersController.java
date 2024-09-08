package club.klabis.members.restapi;

import club.klabis.api.RegistrationNumberApi;
import club.klabis.api.dto.RegistrationNumberGet200ResponseApiDto;
import club.klabis.api.dto.SexApiDto;
import club.klabis.members.domain.MemberService;
import club.klabis.members.domain.RegistrationNumber;
import club.klabis.members.domain.Sex;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class RegistrationNumbersController implements RegistrationNumberApi {

    private final MemberService service;
    private final ConversionService conversionService;

    public RegistrationNumbersController(MemberService service, ConversionService conversionService) {
        this.service = service;
        this.conversionService = conversionService;
    }

    @Override
    public ResponseEntity<RegistrationNumberGet200ResponseApiDto> registrationNumberGet(LocalDate dateOfBirth, SexApiDto sex) {
        RegistrationNumber result = service.suggestRegistrationNumber(dateOfBirth, conversionService.convert(sex, Sex.class));

        RegistrationNumberGet200ResponseApiDto apiDto = RegistrationNumberGet200ResponseApiDto.builder().suggestedRegistrationNumber(result.toRegistrationId()).build();

        return ResponseEntity.ok(apiDto);
    }
}
