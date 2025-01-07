package com.example.paws.mappers;

import com.example.paws.dto.*;
import com.example.paws.entities.*;
import org.mapstruct.*;
import org.springframework.data.domain.Page;

import java.util.List;

@org.mapstruct.Mapper(componentModel = "spring")
public interface Mapper {
    @Mapping(source = "orders", target = "orders", qualifiedByName = "mapOrdersToOrdersDto", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
//    @Mapping(source = "adoptionRequests", target = "adoptionRequests", qualifiedByName = "mapRequestsToRequestsDto", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
//    @Mapping(source = "adoptionListings", target = "adoptionListings", qualifiedByName = "mapListingToListingDto", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(source = "adoptionListings", target = "adoptionListings", qualifiedByName = "mapListingToUserProfileListingDto", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    UserProfileDTO toUserProfileDTO(User user);

    @Named("mapUserToUserDTO")
    UserDTO mapUserToUserDTO(User user);
    @Named("mapOrdersToOrdersDto")
    @Mapping(source = "orderItems", target = "orderItems", qualifiedByName = "mapToOrderItemsDto")
    OrderDTO mapOrdersToOrdersDto(Order order);
    @Named("mapOrdersListToOrdersDtoList")
    @Mapping(source = "orderItems", target = "orderItems", qualifiedByName = "mapToOrderItemsDto")
    List<OrderDTO> mapOrdersListToOrdersDtoList(List<Order> order);

//    @Named("mapListingsToUserProfileListingsDto")
//    @Mapping(source = "images", target = "images", qualifiedByName = "mapImageToImageDTO")
//    @Mapping(source = "adoptionRequests", target = "adoptionRequests", qualifiedByName = "mapRequestToUserProfileRequestDto")
//    @Mapping(source = "user", target = "user", qualifiedByName = "mapUserToUserDTO")
//    List<UserProfileListingDTO> mapListingsToUserProfileListingsDto(List<AdoptionListing> adoptionListings);

    @Named("mapListingsToListingsDto")
    @Mapping(source = "images", target = "images", qualifiedByName = "mapImageToImageDTO")
//    @Mapping(source = "adoptionRequests", target = "adoptionRequests", qualifiedByName = "mapRequestsToRequestsDto")
    @Mapping(source = "user", target = "user", qualifiedByName = "mapUserToUserDTO")
    List<AdoptionListingDTO> mapListingsToListingsDto(List<AdoptionListing> adoptionListing);

    @Named("mapListingToUserProfileListingDto")
    @Mapping(source = "images", target = "images", qualifiedByName = "mapImageToImageDTO")
    @Mapping(source = "adoptionRequests", target = "adoptionRequests", qualifiedByName = "mapRequestToUserProfileRequestDto")
    @Mapping(source = "user", target = "user", qualifiedByName = "mapUserToUserDTO")
    UserProfileListingDTO mapListingToUserProfileListingDto(AdoptionListing adoptionListing);

    @Named("mapRequestToUserProfileRequestDto")
    @Mapping(source = "user", target = "user", qualifiedByName = "mapUserToUserDTO")
    UserProfileRequestDTO mapRequestToUserProfileRequestDto(AdoptionRequest adoptionRequest);
    @Named("mapRequestsToRequestsDto")
    @Mapping(source = "adoptionListing", target = "adoptionListing", qualifiedByName = "mapListingsToListingsDto")
    List<AdoptionRequestDTO> mapRequestsToRequestsDto(List<AdoptionRequest> adoptionRequest);

//    @Named("mapRequestsToUserProfileRequestsDto")
//    List<UserProfileRequestDTO> mapRequestsToUserProfileRequestsDto(List<AdoptionRequest> adoptionRequest);


    @Named("mapImageListToImageDTOList")
    List<ImageDTO> mapImageListToImageDTOList(List<Image> images);

    @Named("mapImageToImageDTO")
    ImageDTO mapImageToImageDTO(Image image);

    @Named("mapToOrderItemsDto")
    List<OrderItemDTO> mapToOrderItemsDto(List<OrderItem> orderItems);
    @Named("mapListingToListingDto")
    @Mapping(source = "petName", target = "petName")
    @Mapping(source = "user", target = "user", qualifiedByName = "mapUserToUserDTO")
    @Mapping(source = "images", target = "images", qualifiedByName = "mapImageToImageDTO")
    AdoptionListingDTO mapListingToListingDto(AdoptionListing adoptionListing);



    @Named("mapToRequest")
//    @Mapping(source = "adoptionListing.listingId", target = "adoptionListing.listingId")
    @Mapping(source = "user.userId", target = "user.userId")
    AdoptionRequest mapToRequest(AdoptionRequestDTO adoptionRequestDTO);





    @Named("mapRequestToRequestDto")
    @Mapping(source = "adoptionListing", target = "adoptionListing", qualifiedByName = "mapListingToListingDto")
    @Mapping(source = "user", target = "user", qualifiedByName = "mapUserToUserDTO")
    AdoptionRequestDTO mapRequestToRequestDto(AdoptionRequest adoptionRequest);






    @Named("mapToOrderItemDto")
    OrderItemDTO mapToOrderItemDto(OrderItem orderItems);
//    @Mapping(source = "petType", target = "petType.id")
//    @Mapping(source = "category", target = "category.categoryId")
    Product toProduct(ProductDTO productDTO);

    @Named("toAdoptionListing")
    @Mapping(source = "petName", target = "petName")
    AdoptionListing toAdoptionListing(AdoptionListingDTO productDTO);



    @Named("mapProductToProductDto")
    @Mapping(source = "images", target = "images", qualifiedByName = "mapImageListToImageDTOList")
//    @Mapping(source = "category.categoryId", target = "category")
//    @Mapping(source = "petType.id", target = "petType")
    ProductDTO mapProductToProductDto(Product product);



    @Named("mapProductListToProductDtoList")
    @Mapping(source = "images", target = "images", qualifiedByName = "mapImageListToImageDTOList")
//    @Mapping(source = "category.categoryId", target = "category")
//    @Mapping(source = "petType.id", target = "petType")
    List<ProductDTO> mapProductListToProductDtoList(List<Product> product);



    //    @Named("mapProductPageToProductDtoPage")
//    @Mapping(source = "images", target = "images", qualifiedByName = "mapImageListToImageDTOList")
//    Page<ProductDTO> mapProductPageToProductDtoPage(Page<Product> product);
    @Mapping(target = "user.userId", source = "userId")
    @Mapping(target = "adoptionListing.listingId", source = "listingId")
    @Mapping(target = "requestDate", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "status", constant = "PENDING")
    AdoptionRequest toAdoptionRequest(Long listingId, Long userId);

}
