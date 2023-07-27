package service;

import persistence.MovieDAO;
import persistence.MovieStub;

public class MovieServiceImpl implements MovieService {

	private MovieDAO movieDAO = new MovieStub(); // Using stub db before we have actual db setup
}
