package org.rallydev.rest.bean;

public abstract class RallyConstants {
	public final static String DEF_STORY = "HierarchicalRequirement";
	public final static String DEF_STORY_STATE = "ScheduleState";

	public enum ScheduleState {
		Backlog("Backlog"), Defined("Defined"), Progress("In-Progress"), Completed(
				"Completed"), Accepted("Accepted");
		private String name;

		ScheduleState(String name) {
			this.name = name;
		}

		public String toString() {
			return name;
		}
	}

	public enum QueryOper {
		Equals("="), NotEq("!="), GreatThan(">"), LessThan("<"), NotGT("<="), NotLT(
				">="), Contains("contains");
		private String name;

		QueryOper(String name) {
			this.name = name;
		}

		public String toString() {
			return name;
		}
	}
}
