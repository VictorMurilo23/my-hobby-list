package com.myhobbylistlmtd.springboot.response.body;

import java.util.List;

import com.myhobbylistlmtd.springboot.reviews.Reviews;

public class ResponseFindReviews {
  /**
   * Lista com as reviews.
   */
  private List<Reviews> reviews;

  /**
   * Número total de páginas.
   */
  private int totalPages;

  /**
   * Constructor.
   * @param totalPages Número total de páginas
   * @param reviews Lista com todas as reviews
   */
  public ResponseFindReviews(
    final int totalPages, final List<Reviews> reviews
  ) {
    this.totalPages = totalPages;
    this.reviews = reviews;
  }

  /**
   * Getter de reviews.
   * @return Uma listas contendo as reviews
   */
  public List<Reviews> getReviews() {
    return reviews;
  }

  /**
   * Setter de reviews.
   * @param reviewsList Uma lista de objetos Reviews
   */
  public void setReviews(final List<Reviews> reviewsList) {
    this.reviews = reviewsList;
  }

  /**
   * Getter de totalPages.
   * @return Retorna o número total de páginas
   */
  public int getTotalPages() {
    return totalPages;
  }

  /**
   * Setter de totalPages.
   * @param pages Um long indicando o número total de páginas
   */
  public void setTotalPages(final int pages) {
    this.totalPages = pages;
  }
}