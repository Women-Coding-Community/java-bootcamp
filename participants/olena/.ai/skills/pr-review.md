Skill: pr-review
Code review workflow that posts inline comments on a pull request using the GitHub CLI.

When to apply
Run this workflow when the user asks you to review a PR, check a pull request, or /pr-review.

Step 1 — Fetch PR data
gh pr view <number> --repo <owner/repo> \
--json title,headRefName,baseRefName,author,body,url

gh pr diff <number> --repo <owner/repo>
If no PR number is given, ask the user to provide one or a GitHub PR URL.

Step 2 — Review mindset
Prioritise findings in this order:

Behavioral regressions — does this break existing functionality?
Security issues — authentication, authorisation, input validation, data exposure
Data integrity — correct transactions, null handling, edge cases
Convention violations — stack-specific patterns (see Step 3)
Test coverage — are new paths exercised?
Validate your assumptions by checking nearby code in the repository before commenting. Keep summaries short; lead with findings.

Step 3 — Stack-specific checks
Apply only the checks relevant to what the PR actually changed.

Java (pure CLI, no framework)
Java 17 idioms: var, switch expressions, text blocks
Error handling: proper exception types, no swallowed exceptions
Naming: consistent with existing codebase style
No Spring, no ORM, no database, no API — pure Java CLI application
Tests
New logic paths have corresponding unit tests
Test names follow shouldDoSomethingWhenCondition camelCase format
Mocks used only where appropriate
Step 4 — Post inline comments
Use gh api to post comments anchored to exact changed lines:

# Get head SHA
gh api repos/<owner>/<repo>/pulls/<number> -q .head.sha

# List files with positions
gh api repos/<owner>/<repo>/pulls/<number>/files --paginate

# Post inline comment
gh api repos/<owner>/<repo>/pulls/<number>/comments \
-X POST \
-f commit_id='<sha>' \
-f path='<file>' \
-F position=<diff-position> \
-f body='<comment>'
Prefer line-level comments over top-level summary comments.

Step 5 — Comment quality bar
Every comment must:

State the concrete risk (what could go wrong)
Point to the exact scenario where it fails
Suggest a minimal fix direction
Include a code snippet or patch example when feasible
Keep tone factual and collaborative — keep the contributor motivated to learn and keep contributing
Useful cleanup commands
# Delete a misplaced comment
gh api repos/<owner>/<repo>/pulls/comments/<id> -X DELETE
gh api repos/<owner>/<repo>/issues/comments/<id> -X DELETE