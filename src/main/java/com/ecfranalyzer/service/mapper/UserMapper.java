// package com.ecfranalyzer.service.mapper;

// // import com.ecfranalyzer.dto.common.AddressDto;
// // import com.ecfranalyzer.dto.common.ContactDetailsDto;
// // import com.ecfranalyzer.dto.common.PersonalDetailsDto;
// import com.ecfranalyzer.dto.common.UserDto;
// import com.ecfranalyzer.dto.request.CreateUserRequest;
// import com.ecfranalyzer.model.*;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.stereotype.Component;

// import java.util.Collections;
// import java.util.stream.Collectors;

// @Component
// public class UserMapper {

//     private final PasswordEncoder passwordEncoder;

//     @Autowired
//     public UserMapper(PasswordEncoder passwordEncoder) {
//         this.passwordEncoder = passwordEncoder;
//     }

//     public User mapCreateUserRequestToUser(CreateUserRequest createUserRequest) {
//         return User.builder()
//                 .email(createUserRequest.getContactDetails().getEmail())
//                 .password(passwordEncoder.encode(createUserRequest.getContactDetails().getPassword()))
//                 .userRoles(Collections.singletonList(UserRole.ROLE_PATIENT))
//                 // .contactDetails(mapContactDetailsDtoToContactDetails(createUserRequest.getContactDetails()))
//                 // .personalDetails(mapPersonalDetailsDtoToPersonalDetails(createUserRequest.getPersonalDetails()))
//                 .build();
//     }

//     public static UserDto mapUserToUserDto(User user) {
//         return UserDto.builder()
//                 .id(user.getId())
//                 .userRoles(user.getUserRoles().stream()
//                         .map(UserRole::name)
//                         .collect(Collectors.toList()))
//                 // .personalDetails(mapBasicPersonalDetailsToPersonalDetailsDto(user.getPersonalDetails()))
//                 // .contactDetails(mapContactDetailsToContactDetailsDto(user.getContactDetails()))
//                 .build();
//     }

//     // public static PersonalDetailsDto mapBasicPersonalDetailsToPersonalDetailsDto(PersonalDetails personalDetails) {
//     //     return PersonalDetailsDto.builder()
//     //             .firstName(personalDetails.getFirstName())
//     //             .lastName(personalDetails.getLastName())
//     //             .address(mapAddressToAddressDto(personalDetails.getAddress()))
//     //             .build();
//     // }

//     // public static ContactDetailsDto mapContactDetailsToContactDetailsDto(ContactDetails contactDetails) {
//     //     return ContactDetailsDto.builder()
//     //             .email(contactDetails.getEmail())
//     //             .build();
//     // }

//     // public static AddressDto mapAddressToAddressDto(Address address) {
//     //     return AddressDto.builder()
//     //             .city(address.getCity())
//     //             .state(address.getState())
//     //             .postalCode(address.getPostalCode())
//     //             .build();
//     // }

//     // public static Address mapAddressDtoToAddress(AddressDto address) {
//     //     return Address.builder()
//     //             .streetAddressLine1(address.getStreetAddressLine1())
//     //             .streetAddressLine2(address.getStreetAddressLine2())
//     //             .city(address.getCity())
//     //             .state(address.getState())
//     //             .postalCode(address.getPostalCode())
//     //             .countryCode(address.getCountryCode())
//     //             .build();
//     // }

//     // public static PersonalDetails mapPersonalDetailsDtoToPersonalDetails(PersonalDetailsDto personalDetailsDto) {
//     //     return PersonalDetails.builder()
//     //             .firstName(personalDetailsDto.getFirstName())
//     //             .middleName(personalDetailsDto.getMiddleName())
//     //             .lastName(personalDetailsDto.getLastName())
//     //             .ssn(personalDetailsDto.getSsn())
//     //             .dob(personalDetailsDto.getDob())
//     //             .sex(personalDetailsDto.getSex())
//     //             .address(mapAddressDtoToAddress(personalDetailsDto.getAddress()))
//     //             .build();
//     // }

//     // public static ContactDetails mapContactDetailsDtoToContactDetails(ContactDetailsDto contactDetailsDto) {
//     //     return ContactDetails.builder()
//     //             .email(contactDetailsDto.getEmail())
//     //             .mobilePhoneNumber(contactDetailsDto.getMobilePhone())
//     //             .build();
//     // }

// }

