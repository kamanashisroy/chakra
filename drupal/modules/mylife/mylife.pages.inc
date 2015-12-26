<?php

/**
 * @file
 * Page callback file for the mylife module.
 */

/**
 * Menu callback; displays a Drupal page containing recent mylife entries of a given user.
 */
function mylife_page_default() {
  return 'Default page is empty (TODO redirect to user page)';
}

/**
 * Menu callback; displays a Drupal page containing recent mylife entries of a given user.
 */
function mylife_page_user($account) {
  global $user;

  drupal_set_title($title = t("@name's activity", array('@name' => format_username($account))), PASS_THROUGH);

  return "TODO show the graph of activites and his ponints.";
}
