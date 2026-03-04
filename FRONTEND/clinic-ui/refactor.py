import os
import glob
import re

files = glob.glob('pages/*.html')

for filepath in files:
    with open(filepath, 'r', encoding='utf-8') as f:
        content = f.read()

    # Update CSS link
    content = content.replace('<link rel="stylesheet" href="style.css">', 
                              '<link rel="stylesheet" href="../css/style.css">\n    <link rel="stylesheet" href="../css/layout.css">')

    # Update JS link
    content = content.replace('<script src="components.js"></script>', 
                              '<script src="../js/layout.js"></script>\n    <script src="../js/dashboard.js"></script>')

    # Skip login/register for layout restructuring
    if 'login.html' in filepath or 'register.html' in filepath:
        with open(filepath, 'w', encoding='utf-8') as f:
            f.write(content)
        continue

    # Standardize Layout
    # 1. <div class="admin-layout"> or <body class="patient-layout"> goes to standard structure
    # Let's replace placeholder ids
    content = re.sub(r'id=["\']\w*-sidebar-placeholder["\']', 'id="sidebar"', content)
    content = re.sub(r'id=["\']\w*-navbar-placeholder["\']', 'id="navbar"', content)
    
    # Patient pages didn't have sidebar placeholder.
    if 'id="sidebar"' not in content:
        # Inject it right after body
        content = re.sub(r'(<body[^>]*>)', r'\1\n    <div class="app-layout" style="display: flex; min-height: 100vh;">\n        <div id="sidebar"></div>\n        <div class="main-content" style="flex:1; display:flex; flex-direction:column;">', content)
        # And inject navbar inside main-content
        if 'id="navbar-placeholder"' in content:
            content = content.replace('<div id="navbar-placeholder"></div>', '<div id="navbar"></div>\n            <div class="content-area" style="padding: 2rem; overflow-y: auto;">')
        
        # Close the divs before body ends
        content = content.replace('</body>', '            <div id="footer"></div>\n            </div>\n        </div>\n    </body>')
        
    else:
        # Admin pages
        content = content.replace('class="admin-layout"', 'class="app-layout" style="display: flex; min-height: 100vh;"')
        # Ensure footer is at the end of main-content
        if '<div id="footer"></div>' not in content:
            content = content.replace('</main>', '    <div id="footer"></div>\n        </main>')
    
    with open(filepath, 'w', encoding='utf-8') as f:
        f.write(content)

print("Done refactoring.")
