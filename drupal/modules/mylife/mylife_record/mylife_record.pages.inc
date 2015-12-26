<?php

/**
 * @file
 * Page callback file for the mylife_record module.
 */

/**
 * Menu callback; displays a Drupal page containing recent mylife_record entries of a given user.
 */
function mylife_record_page_default() {
  return 'Default page is empty (TODO redirect to user page)';
}

/**
 * Menu callback; displays a Drupal page containing recent mylife_record entries of a given user.
 */
function mylife_record_page_user($account) {
  global $user;

  drupal_set_title($title = t("@name's mylife_record", array('@name' => format_username($account))), PASS_THROUGH);

  return "TODO show the links to add record";
}
