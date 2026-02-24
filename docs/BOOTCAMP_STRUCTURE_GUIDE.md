# Bootcamp Structure & Workflow Guide

> 📋 **For Mentors & Organizers:** This document explains the complete bootcamp structure, workflow, and how to maintain learning resources.

## 📚 Document Ecosystem Overview

```
┌─────────────────────────────────────────────────────────────┐
│                    MAIN README.md                           │
│                 (Entry point for all)                       │
│  - Overview                                                 │
│  - Quick links to all resources                             │
│  - Featured guides                                          │
└──────────────┬──────────────────────────────────────────────┘
               │
       ┌───────┴────────┐
       │                │
       ▼                ▼
┌──────────────┐  ┌──────────────┐
│ LEARNING_PATH│  │ SESSION_NOTES│
│              │  │              │
│ - Roadmap    │  │ - Live notes │
│ - Topics     │  │ - Q&A        │
│ - Progress   │  │ - Recordings │
│   tracker    │  │              │
└──────┬───────┘  └──────┬───────┘
       │                 │
       │    ┌────────────┴────────────┐
       │    │                         │
       ▼    ▼                         ▼
┌─────────────────┐          ┌──────────────┐
│ GUIDES (Topic)  │          │     FAQ      │
│                 │          │              │
│ - In-depth      │◄─────────│ - Common Q's │
│ - Exercises     │  links   │ - Solutions  │
│ - Examples      │          │              │
└─────────────────┘          └──────────────┘
```

## 🔄 Bootcamp Session Workflow

### Before Session
1. **Mentors prepare:**
   - Check participant questions from Slack
   - Check current new PRs with projects and questions 

2. **Participants prepare:**
   - Review [Session Notes](./SESSION_NOTES.md) from last session
   - Complete action items
   - Submit questions in advance

### During Session (90 min suggested)
**Structure:**
```
Quick recap of last session
Check attendency tracking
Q&A and deeper dive into questions
Hands-on exercise / live debugging
```

**Capture:**
- ✅ Questions raised (for FAQ)
- ✅ Common mistakes (for guides)
- ✅ Demo code (for session notes)
- ✅ Action items for participants

### After Session
**Coordinators:**

1. **Update SESSION_NOTES.md**
   ```markdown
   ## Session X: [Topic]
   - Add attendee count
   - Summarize key takeaways
   - Document Q&A
   - Link to created guides
   ```

2. **Create/Update Guides** (if needed)
   - Use the [Guide Template](#guide-template)
   - Focus on questions that came up multiple times
   - Include real examples from the session

3. **Update FAQ.md**
   - Add new questions with answers
   - Link to relevant guides
   - Use actual participant language

4. **Update LEARNING_PATH.md**
   - Mark topics as covered
   - Add links to new guides
   - Update progress tracking

5. **Commit and push**
   ```bash
   git add docs/
   git commit -m "Session X: Add notes, guides, and FAQ updates"
   git push
   ```

## 📝 Guide Template

When creating a new guide, use this structure for consistency:

```markdown
# [Topic Name]

> 📝 **Bootcamp Note:** [Context about when/why this topic came up in sessions]

---

## What is [Topic]?

[Brief explanation - 2-3 paragraphs max]

---

## Why It Matters

[Real-world relevance, connection to projects, interview importance]

---

## The Basics

### Concept 1
[Explanation with code example]

```java
// ✅ Good example
// Clear, commented code

// ❌ Common mistake
// Show the wrong way too
```

### Concept 2
[More examples]

---

## Common Pitfalls

### Mistake 1: [Description]
**Problem:**
```java
// ❌ Wrong code that participants often write
```

**Why it fails:** [Explanation]

**Solution:**
```java
// ✅ Correct approach
```

*→ Added to [FAQ #X](../FAQ.md#X)*

---

## Modern Java Approach (if applicable)

[How Java 21+ makes this better/different]

```java
// Old way
// ...

// Java 21 way
// ...
```

---

## Practice Exercises

### Exercise 1: [Name]
```java
// Starter code or description
```

<details>
<summary>Click to see solution</summary>

```java
// Solution with explanation
```
</details>

---

## Real-World Examples

### Example from WCC Platform
[Actual example from the project, if applicable]

### Example from Common Projects
[Generic examples participants might encounter]

---

## Best Practices Summary

### ✅ Do:
- Practice 1
- Practice 2

### ❌ Avoid:
- Anti-pattern 1
- Anti-pattern 2

### 🔄 Prefer modern alternatives:
| Old approach | Modern Java 21 approach |
|--------------|-------------------------|
| Old way      | New way                |

---

## Quick Reference

```java
// Cheat sheet - commonly used patterns
```

---

## Resources

- [Official Java Docs](link)
- [Related Guide](../guides/other-guide.md)
- [FAQ Entry](../FAQ.md#relevant-question)

---

*This guide is part of the [WCC Java Bootcamp](../../README.md)*

*Last updated: YYYY-MM-DD | Session X*
```

### Guide Checklist
Before publishing a guide:
- [ ] Clear learning objectives stated upfront
- [ ] Code examples with both ❌ wrong and ✅ correct versions
- [ ] Practice exercises with solutions (use `<details>` for spoilers)
- [ ] Connection to real-world use cases
- [ ] Links to related guides and FAQ entries
- [ ] Beginner-friendly language (jargon explained)
- [ ] Added to [guides/README.md](./guides/README.md)
- [ ] Linked from [LEARNING_PATH.md](./LEARNING_PATH.md)
- [ ] Mentioned in [SESSION_NOTES.md](./SESSION_NOTES.md)
- [ ] Common questions added to [FAQ.md](./FAQ.md)
- [ ] Main README updated if it's a featured guide

## 🎯 FAQ Best Practices

### When to Add to FAQ

Add an entry when:
- Same question asked 2+ times
- Question comes up in multiple sessions
- Topic causes visible confusion
- Common error message
- Setup/tooling issue

### FAQ Entry Template

```markdown
### X. [Question in natural language]

**[Sub-heading if needed]**

[Brief explanation of why this happens]

**Problem:**
```java
// ❌ Code that causes the issue
```

**Solutions:**
```java
// ✅ Solution 1
// ✅ Solution 2 (if multiple approaches)
```

**Related Guide:** [Guide Name](./guides/guide-name.md#section)
```

### FAQ Organization
- Group by category (Bootcamp Logistics, Java Fundamentals, etc.)
- Number entries for easy reference
- Link bidirectionally (FAQ ↔ Guides)
- Keep answers concise (link to guides for depth)
- Update as better solutions emerge

## 📊 Metrics to Track

### Participation Metrics
- Session attendance
- Active Slack participants
- Projects submitted
- Guide views (if tracked)

### Content Metrics
- Number of guides published
- FAQ entries added
- Common questions addressed
- Topics covered vs planned

### Quality Indicators
- Repeat questions (should decrease over time)
- Guide update frequency
- Participant feedback
- Certificate completion rate

## 🤝 Contribution Workflow

### For Participants

**Found a mistake?**
```bash
1. Open an issue
2. Or fix it directly and submit PR
3. Small typos → direct PR
```

**Want to suggest a guide topic?**
```
1. Post in #java-bootcamp
2. Mention in a session
3. Create GitHub discussion
```

### For Mentors

**Adding a new guide:**
```bash
1. Create guide using template
2. Add to docs/guides/
3. Update docs/guides/README.md (index)
4. Update LEARNING_PATH.md (link in roadmap)
5. Update SESSION_NOTES.md (session context)
6. Add related FAQs
7. Update main README if featured guide
```

**After each session:**
```bash
1. Update SESSION_NOTES.md
2. Create/update guides as needed
3. Add FAQs from Q&A
4. Commit with clear message:
   "Session X: [Topic] - notes, guides, FAQs"
```

## 🔄 Maintenance Schedule

### Weekly (After each session)
- [ ] Update SESSION_NOTES.md
- [ ] Create new guides if needed
- [ ] Add FAQs from session Q&A
- [ ] Review and respond to participant questions

### Bi-weekly
- [ ] Review FAQ for outdated answers
- [ ] Check LEARNING_PATH.md progress
- [ ] Update "Coming Soon" sections
- [ ] Scan Slack for emerging patterns

### End of Bootcamp
- [ ] Archive session notes
- [ ] Create "Complete Guide Index"
- [ ] Gather participant feedback
- [ ] Update templates for next cohort

## 📁 File Ownership

| File | Primary Maintainer | Update Frequency |
|------|-------------------|------------------|
| README.md | Lead Mentor | As needed |
| LEARNING_PATH.md | Lead Mentor | Weekly |
| SESSION_NOTES.md | Session Mentor | After each session |
| FAQ.md | All Mentors | After each session |
| guides/*.md | Topic Mentor | As needed |
| guides/README.md | Lead Mentor | When guides added |

## 🎓 Success Criteria

### For Participants
- ✅ Clear path from beginner to project completion
- ✅ Can find answers to common questions quickly
- ✅ Understand what to focus on vs skip
- ✅ Feel supported throughout the journey

### For Mentors
- ✅ Repeatable session structure
- ✅ Easy to maintain documentation
- ✅ Common questions don't need repeated answers
- ✅ Content builds organically from sessions

### For the Community
- ✅ Resources useful beyond bootcamp
- ✅ Shareable with future cohorts
- ✅ Showcases WCC's teaching approach
- ✅ Participants become future mentors

---

## 🚀 Quick Start for New Mentors

1. **Read this guide**
2. **Review existing guides** to understand the style
3. **Join a session** as observer first
4. **Lead a topic** you're comfortable with
5. **Create your first guide** using the template
6. **Update FAQ** with questions you answer

## 📞 Questions?

- **About structure:** Post in #java-bootcamp-mentors
- **About content:** Review existing guides for examples
- **Technical issues:** Create GitHub issue

---

*This is a living document. Improve it as you learn what works!*

*Last updated: 2026-02-19*