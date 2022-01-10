package br.com.erudio;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.erudio.exception.UnsupportedMathOperationException;

@RestController
public class MathController {

	// @RequestParam = param opcional

	@RequestMapping(value = "/sum/{numberOne}/{numberTwo}", method = RequestMethod.GET)
	public Double sum(@PathVariable(value = "numberOne") String numberOne,
			@PathVariable(value = "numberTwo") String numberTwo) {
		if (!isNumeric(numberOne) || !isNumeric(numberTwo)) {
			throw new UnsupportedMathOperationException("Is not a number! Set a numeric value!");
		}

		Double sum = convertToDouble(numberOne) + convertToDouble(numberTwo);
		return sum;
	}

	@RequestMapping(value = "/subtraction/{numberOne}/{numberTwo}", method = RequestMethod.GET)
	public Double subtraction(@PathVariable(value = "numberOne") String numberOne,
			@PathVariable(value = "numberTwo") String numberTwo) {
		if (!isNumeric(numberOne) || !isNumeric(numberTwo)) {
			throw new UnsupportedMathOperationException("Is not a number! Set a numeric value!");
		}

		Double subtraction = convertToDouble(numberOne) - convertToDouble(numberTwo);
		return subtraction;
	}

	@RequestMapping(value = "/multiplication/{numberOne}/{numberTwo}", method = RequestMethod.GET)
	public Double multiplication(@PathVariable(value = "numberOne") String numberOne,
			@PathVariable(value = "numberTwo") String numberTwo) {
		if (!isNumeric(numberOne) || !isNumeric(numberTwo)) {
			throw new UnsupportedMathOperationException("Is not a number! Set a numeric value!");
		}

		Double multiplication = convertToDouble(numberOne) * convertToDouble(numberTwo);
		return multiplication;
	}

	@RequestMapping(value = "/division/{numberOne}/{numberTwo}", method = RequestMethod.GET)
	public Double division(@PathVariable(value = "numberOne") String numberOne,
			@PathVariable(value = "numberTwo") String numberTwo) {
		if (!isNumeric(numberOne) || !isNumeric(numberTwo)) {
			throw new UnsupportedMathOperationException("Is not a number! Set a numeric value!");
		}

		Double division = convertToDouble(numberOne) / convertToDouble(numberTwo);
		return division;
	}

	@RequestMapping(value = "/mean/{numberOne}/{numberTwo}", method = RequestMethod.GET)
	public Double mean(@PathVariable(value = "numberOne") String numberOne,
			@PathVariable(value = "numberTwo") String numberTwo) {
		if (!isNumeric(numberOne) || !isNumeric(numberTwo)) {
			throw new UnsupportedMathOperationException("Is not a number! Set a numeric value!");
		}

		Double mean = (convertToDouble(numberOne) + convertToDouble(numberTwo)) / 2;
		return mean;
	}

	@RequestMapping(value = "/squareRoot/{numberOne}", method = RequestMethod.GET)
	public Double squareRoot(@PathVariable(value = "numberOne") String numberOne) {
		if (!isNumeric(numberOne)) {
			throw new UnsupportedMathOperationException("Is not a number! Set a numeric value!");
		}

		Double squareRoot = (Double) Math.sqrt(convertToDouble(numberOne));
		return squareRoot;
	}

	private double convertToDouble(String strNumber) {
		if (isNumeric(strNumber))
			return Double.parseDouble(strNumber);
		return 0D;
	}

	private boolean isNumeric(String strNumber) {
		if (strNumber == null)
			return false;
		String number = strNumber.replace(",", ".");
		return number.matches("[-+]?[0-9]*\\.?[0-9]+");
	}

}
