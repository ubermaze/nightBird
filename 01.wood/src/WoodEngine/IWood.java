﻿package WoodEngine;

import java.io.IOException;



public interface IWood {
	/**
	 * Создает нового лесного жителя.
	 * 
	 * @param name имя лесного жителя
	 * @param start место появления
	 * @throws IOException 
	 */
	void createWoodman(String name, Point start) throws IOException;
	
	/**
	 * Перемещает лесного жителя.
	 * 
	 * @param name имя лесного жителя
	 * @param direction направление перемещения
	 * @return результат перемещения
	 */
	Action move(String name, Direction direction);
}
