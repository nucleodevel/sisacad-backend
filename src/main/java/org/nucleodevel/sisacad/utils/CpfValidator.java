package org.nucleodevel.sisacad.utils;

import java.util.Arrays;

public class CpfValidator {

	public static boolean isValid(String field) {
		String cpf = removePontuacao(field);
		String[] listaDeCpfInvalidos = new String[] { "11111111111", "22222222222", "33333333333", "44444444444",
				"55555555555", "66666666666", "77777777777", "88888888888", "99999999999", "00000000000" };

		if (cpf.length() != 11)
			return false;

		// Verifica se cpf � formado por numeros iguais
		if (Arrays.binarySearch(listaDeCpfInvalidos, cpf) >= 0)
			return false;

		String c = cpf.substring(0, 9);
		String dv = cpf.substring(9, 11);

		int d1 = 0;

		for (int i = 0; i < 9; i++)
			d1 += converteChatToInt(c.charAt(i)) * (10 - i);

		if (d1 == 0)
			return false;

		d1 = 11 - (d1 % 11);
		if (d1 > 9)
			d1 = 0;

		if (converteChatToInt(dv.charAt(0)) != d1)
			return false;

		d1 *= 2;
		for (int i = 0; i < 9; i++)
			d1 += converteChatToInt(c.charAt(i)) * (11 - i);

		d1 = 11 - (d1 % 11);
		if (d1 > 9)
			d1 = 0;

		if (converteChatToInt(dv.charAt(1)) != d1)
			return false;

		return true;
	}

	public static String removePontuacao(String cpf) {
		return cpf.replaceAll("[.-]", "");
	}

	private static Integer converteChatToInt(char character) {
		return Integer.parseInt(Character.toString(character));
	}

}
