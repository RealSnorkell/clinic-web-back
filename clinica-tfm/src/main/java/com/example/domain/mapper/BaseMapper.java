package com.example.domain.mapper;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

public interface BaseMapper<I, O> {

	/**
	 * Method used to transform (Map) object I to object O.
	 * 
	 * @param I input
	 * @return O output
	 */
	O fromInputToOutput(I input);

	/**
	 * Method used to transform (Map) object O to object I.
	 * 
	 * @param O output
	 * @return I input
	 */
	I fromOutputToInput(O output);

	/**
	 * Method used to transform (Map) a List of O objects to a List of I objects.
	 * 
	 * @param List<I> inputList
	 * @return List<O> outputList
	 */
	List<O> fromInputToOutput(List<I> inputList);

	/**
	 * Method used to transform (Map) a List of I objects to a List of O objects.
	 * 
	 * @param List<O> outputList
	 * @return List<I> inputList
	 */
	List<I> fromOutputToInput(List<O> outputList);

	/**
	 * Method used to transform (Map) an Optional of O to an Optional of I.
	 * 
	 * @param Optional<I> input
	 * @return Optional<O> output
	 */
	default Optional<O> fromInputToOutput(Optional<I> input) {
		return input.isPresent() ? Optional.<O>of(fromInputToOutput(input.get())) : Optional.<O>empty();
	}

	/**
	 * Method used to transform (Map) an Optional of I to an Optional of O.
	 * 
	 * @param Optional<O> output
	 * @return Optional<I> input
	 */
	default Optional<I> fromOutputToInput(Optional<O> output) {
		return output.isPresent() ? Optional.<I>of(fromOutputToInput(output.get())) : Optional.<I>empty();
	}

	/**
	 * Method used to transform (Map) a Page of I objects to a Page of O objects.
	 * 
	 * @param Page<I> inputPage
	 * @return Page<O> outputPage
	 */
	default Page<O> fromInputToOutput(Page<I> inputPage) {
		return inputPage == null ? null
				: new PageImpl<>(fromInputToOutput(inputPage.getContent()), inputPage.getPageable(),
						inputPage.getTotalElements());
	}

	/**
	 * Method used to transform (Map) a Page of O objects to a Page of I objects.
	 * 
	 * @param Page<O> outputPage
	 * @return Page<I> inputPage
	 */
	default Page<I> fromOutputToInput(Page<O> outputPage) {
		return outputPage == null ? null
				: new PageImpl<>(fromOutputToInput(outputPage.getContent()), outputPage.getPageable(),
						outputPage.getTotalElements());
	}
}