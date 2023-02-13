package com.example.UserService.User.service;

import com.example.UserService.User.dto.Address.Request.CreateAddressRequest;
import com.example.UserService.User.dto.Address.Request.DeleteAddressRequest;
import com.example.UserService.User.dto.Address.Request.FindAddressRequest;
import com.example.UserService.User.dto.Address.Request.UpdateAddressRequest;
import com.example.UserService.User.dto.Address.Response.AddressDto;
import com.example.UserService.User.dto.Address.Response.ListAddressResponse;
import com.example.UserService.model.Address;
import com.example.UserService.repository.AddressRepository;
import com.example.UserService.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AddressService {

    private final UserRepository userRepository;

    private final AddressRepository addressRepository;

    public void createAddress(CreateAddressRequest createAddressRequest){
        userRepository.findByUid(createAddressRequest.getUid()).ifPresentOrElse(
                user ->{
                    Address address = Address.builder()
                            .receiverAddress(createAddressRequest.getAddress())
                            .receiverName(createAddressRequest.getName())
                            .phoneNumber(createAddressRequest.getPhoneNumber())
                            .user(user)
                            .build();
                    addressRepository.save(address);
                    log.info("Address {} is saved successfully", address.getId());
                },
                () -> log.info("User {} is not available", createAddressRequest.getUid())
        );

    }

    private AddressDto mapToAddressDto(Address address){
        return AddressDto.builder()
                .id(address.getId())
                .receiverName(address.getReceiverName())
                .receiverAddress(address.getReceiverAddress())
                .phoneNumber(address.getPhoneNumber())
                .build();
    }

    public ListAddressResponse findAllByUid(FindAddressRequest findAddressRequest) {
        return ListAddressResponse.builder()
                .addressDtoList(addressRepository.findAllByUserUid(findAddressRequest.getUid(),
                                PageRequest.of(findAddressRequest.getCurrentPage(), findAddressRequest.getSize()))
                        .get().map(this::mapToAddressDto).toList())
                .currentPage(findAddressRequest.getCurrentPage())
                .size(findAddressRequest.getSize())
                .totalPage((addressRepository.countAddressByUserUid(findAddressRequest.getUid()) / findAddressRequest.getSize()) + 1)
                .build();
    }

    public void updateAddress(UpdateAddressRequest updateAddressRequest) {
        addressRepository.findByUserUidAndId(updateAddressRequest.getUid(), updateAddressRequest.getId()).ifPresentOrElse(
                address -> {
                    address.setReceiverAddress(updateAddressRequest.getAddress() != null ?
                            updateAddressRequest.getAddress() : address.getReceiverAddress());
                    address.setReceiverName(address.getReceiverName() != null ?
                            updateAddressRequest.getName() : address.getReceiverName());
                    address.setPhoneNumber(updateAddressRequest.getPhoneNumber() != null ?
                            updateAddressRequest.getPhoneNumber() : address.getPhoneNumber());
                    addressRepository.save(address);
                    log.info("Address {} is updated successfully", updateAddressRequest.getId());
                },
                () -> log.info("Address {} of user {} is not available",updateAddressRequest.getUid(), updateAddressRequest.getId())
        );
    }

    public void deleteAddress(DeleteAddressRequest deleteAddressRequest) {
        addressRepository.findByUserUidAndId(deleteAddressRequest.getUid(), deleteAddressRequest.getId()).ifPresentOrElse(
                address -> {
                    addressRepository.delete(address);
                    log.info("Address {} is deleted successfully", deleteAddressRequest.getId());
                },
                () -> log.info("Address {} is is not available", deleteAddressRequest.getId())
        );
    }
}
