filetype plugin indent on							                                          "Enable plugins and indents by filetype
syntax enable                   						                                    "Turn on syntax highlighting

set cursorline      " Highlight current line
set colorcolumn=80  " highlight the 80 columns

set background=dark " Background color for better constrast

set termguicolors   " use true-colors in terminal
set title           " change the terminal's title

set clipboard=unnamed           						                                    "To enable copy paste in vim

" using different cursor size for insert mode and normal mode
set guicursor=n-v-c:block-Cursor/lCursor-blinkon0,i-ci:ver25-Cursor/lCursor,r-cr:hor20-Cursor/lCursor

" sets the status line
set statusline="%f%m%r%h%w [%Y] [0x%02.2B]%< %F%=%4v,%4l %3p%% of %L"

"-------------------------- UI stuff ends here -----------------------

" }}}

" Common settings {{{

set encoding=utf-8
set showcmd                    " display incomplete commands


set splitright                 " opening buffer on the right
set splitbelow                 "Set up new horizontal splits positions
set nowrap                     " don't wrap lines

let mapleader = ","            " mapping leader(\) to ,

set wildmode=longest,list,full  						                                    "bash-like tab completion.
set wildmenu

" this is done to allow webpack to do auto complilation
" this setting will disable save write
set backupcopy=yes

" using all the colors 256 versions
let g:rehash256 = 1

set lazyredraw " redraw only when we need to.
set ttyfast    " Send more characters at a given time.

set ruler " Ruler on
set clipboard+=unnamed

set path+=**                                                                    "Allow recursive search

set history=500									                                                "show lots of cmd line history

set gcr=a:blinkon500-blinkwait500-blinkoff500 "set cursor blinking rate
" }}}

"  Tabs and Spaces {{{
" --------------------------------------------------------------------
"  TABS AND SPACES
" --------------------------------------------------------------------

set tabstop=2 shiftwidth=2     " a tab is two spaces (or set this to 4)
set expandtab                  " use spaces, not tabs (optional)
set backspace=indent,eol,start " backspace through everything in insert mode

" --------------------------------------------------------------------
"  Line numbers
" --------------------------------------------------------------------
"  Setting relative line number in normal mode and absolute in relative mode
set number relativenumber

" we are using auto cmd group because if the same autocmd has been
" defined twice in vimrc, vim will not replace the earlier one, it
" will keep both. So auto cmd groups will remove this possibility.
" The first command 'autocmd!' clears everything in the group.

augroup numbertoggle
  autocmd!
  autocmd BufEnter,FocusGained,InsertLeave * set relativenumber
  autocmd BufLeave,FocusLost,InsertEnter   * set norelativenumber
augroup END

" --------------------- TABS AND SPACES ends here  ------------------
" }}}

" Indentation {{{
" --------------------------------------------------------------------
" INDENTATION
" --------------------------------------------------------------------
set cindent
set autoindent
set smarttab
set expandtab
set cpoptions+=$

" ------------------------ INDENTATION ENDS HERE --------------------
" }}}

" set spell spelllang=en_us       " enabling spelling check

set autoread                    " re-load files automatically

"" Visual
set showmatch

"" Ctags support
set tags=./tags;,tags;           " look for a tags file in the directory of the current file,
" then upward until / and in the working directory, then upward until /

" let g:easytags_dynamic_files = 1 " project specific ctags generation
" let g:easytags_async = 1         " Do not block vim do work in async

"" Support for ack
set grepprg=rg\ --vimgrep

" Searching {{{
" --------------------------------------------------------------------
"  SEARCHING
" --------------------------------------------------------------------

set hlsearch                    " highlight matches
set incsearch                   " incremental searching
set ignorecase                  " searches are case insensitive...
set smartcase                   " ... unless they contain at least one capital letter

" ------------------------ SEARCHING ENDS HERE ----------------------
" }}}


set inccommand=split "Show substitute changes immidiately in separate split
set fillchars+=vert:\│ "Make vertical split separator full line


set linebreak                     "Wrap lines at convenient points
set listchars=tab:\ \ ,trail:·    "Set trails for tabs and spaces
set list                          "Enable listchars


set pumheight=30                "Maximum number of entries in autocomplete popup
set ttimeoutlen=0               "Reduce Command timeout for faster escape and O
set fileencoding=utf-8          "Set utf-8 encoding on write


" ================ Scrolling ======================== {{{

set scrolloff=8                                                                 "Start scrolling when we're 8 lines away from margins
set sidescrolloff=15
set sidescroll=5
" }}}

" ================ Abbreviations ==================== {{{

cnoreabbrev Wq wq
cnoreabbrev WQ wq
cnoreabbrev W w
cnoreabbrev Q q
cnoreabbrev Qa qa
cnoreabbrev Bd bd
cnoreabbrev bD bd
cnoreabbrev wrap set wrap
cnoreabbrev nowrap set nowrap
cnoreabbrev bda BufOnly
cnoreabbrev f find
cnoreabbrev F find

" }}}



"install vim-plug if not installed already
if empty(glob('~/.vim/autoload/plug.vim'))
  silent execute "!curl -fLo ~/.vim/autoload/plug.vim --create-dirs https://raw.githubusercontent.com/junegunn/vim-plug/master/plug.vim"
  autocmd VimEnter * PlugInstall | source '~/.vimrc'
endif

call plug#begin('~/.vim/plugged')

"""" Base Plugins -----------------------------------
Plug 'honza/vim-snippets'
Plug 'tomtom/tcomment_vim'                         " source for gcc command for comments
Plug 'tpope/vim-fugitive'
Plug 'tpope/vim-rhubarb'                           "needed by fugitive to browse the file on github/remote
Plug 'mtth/scratch.vim'                            " put anything in scratch buffer, no need to create any file
Plug 'SirVer/ultisnips'                            " snippet pluggin
Plug 'tpope/vim-surround'                          " change or delete surrounding text
Plug 'flazz/vim-colorschemes'
Plug 'yggdroot/indentline'
Plug 'Raimondi/delimitMate'                        " Provides automatic closing quotes, parens
Plug 'airblade/vim-gitgutter'                      " Git gutter support for vim
Plug 'vim-scripts/BufOnly.vim'                     " delete all buffers but current one
Plug 'tpope/vim-obsession'                         " vim session management
Plug 'junegunn/fzf', { 'dir': '~/.fzf', 'do': './install --all' }
Plug 'junegunn/fzf.vim'
Plug 'tpope/vim-commentary'
Plug 'vim-airline/vim-airline'
Plug 'vim-airline/vim-airline-themes'


" On-demand loading
Plug 'scrooloose/nerdtree'
" Plug 'scrooloose/nerdtree', { 'on':  'NERDTreeToggle' }
Plug 'Xuyuanp/nerdtree-git-plugin'
" Plug 'Xuyuanp/nerdtree-git-plugin', { 'on': 'NERDTreeToggle' }
Plug 'tiagofumo/vim-nerdtree-syntax-highlight'

call plug#end()

"" Nerd Tree specific {{{
augroup nerdtree
  autocmd!
  autocmd StdinReadPre * let s:std_in=1
  noremap <C-b> :NERDTreeToggle<CR>
  nnoremap rin :NERDTree %<CR>   "open nerd tree for current dir
  autocmd bufenter * if (winnr("$") == 1 && exists("b:NERDTree") && b:NERDTree.isTabTree()) | q | endif
augroup END

" close nerdtree after file gets opened
" let NERDTreeQuitOnOpen=1
" }}}


" vim airline {{{
let g:airline#extensions#tabline#enabled = 1
set laststatus=2

let g:airline#extensions#tabline#buffer_idx_mode = 1
nmap <leader>1 <Plug>AirlineSelectTab1
nmap <leader>2 <Plug>AirlineSelectTab2
nmap <leader>3 <Plug>AirlineSelectTab3
nmap <leader>4 <Plug>AirlineSelectTab4
nmap <leader>5 <Plug>AirlineSelectTab5
nmap <leader>6 <Plug>AirlineSelectTab6
nmap <leader>7 <Plug>AirlineSelectTab7
nmap <leader>8 <Plug>AirlineSelectTab8
nmap <leader>9 <Plug>AirlineSelectTab9
nmap <S-Tab> <Plug>AirlineSelectPrevTab
nmap <Tab> <Plug>AirlineSelectNextTab


" Airline configuration
let g:airline#extensions#tabline#enabled = 1
let g:airline#extensions#tabline#fnamenod = ':t'
let g:airline_powerline_fonts = 1
" let g:airline_theme='onedark'
" let g:airline_theme='materialmonokai'
" }}}

" Ulti snip settins {{{
let g:UltiSnipsExpandTrigger='<C-j>'
let g:UltiSnipsJumpForwardTrigger='<C-j>'
let g:UltiSnipsJumpBackwardTrigger='<C-k>'
let g:UltiSnipsSnippetsDir='~/.config/nvim/custom_snippets'
" Setting the search path for snippets
let g:UltiSnipsSnippetDirectories=["UltiSnips", "custom_snippets"]
" }}}

" FZF related settings {{{
" This is the default extra key bindings
let g:fzf_action = {
      \ 'ctrl-t': 'tab split',
      \ 'ctrl-x': 'split',
      \ 'ctrl-v': 'vsplit' }

" Default fzf layout
" - down / up / left / right
let g:fzf_layout = { 'down': '~40%' }

" In Neovim, you can set up fzf window using a Vim command
" let g:fzf_layout = { 'window': 'enew' }
" let g:fzf_layout = { 'window': '-tabnew' }

" Customize fzf colors to match your color scheme
let g:fzf_colors =
      \ { 'fg':      ['fg', 'Normal'],
      \ 'bg':      ['bg', 'Normal'],
      \ 'hl':      ['fg', 'Comment'],
      \ 'fg+':     ['fg', 'CursorLine', 'CursorColumn', 'Normal'],
      \ 'bg+':     ['bg', 'CursorLine', 'CursorColumn'],
      \ 'hl+':     ['fg', 'Statement'],
      \ 'info':    ['fg', 'PreProc'],
      \ 'prompt':  ['fg', 'Conditional'],
      \ 'pointer': ['fg', 'Exception'],
      \ 'marker':  ['fg', 'Keyword'],
      \ 'spinner': ['fg', 'Label'],
      \ 'header':  ['fg', 'Comment'] }

" Enable per-command history.
" CTRL-N and CTRL-P will be automatically bound to next-history and
" previous-history instead of down and up. If you don't like the change,
" explicitly bind the keys to down and up in your $FZF_DEFAULT_OPTS.
let g:fzf_history_dir = '~/.local/share/fzf-history'

" [Buffers] Jump to the existing window if possible
" let g:fzf_buffers_jump = 1 "It was bit slow
let g:fzf_commits_log_options = '--graph --color=always --format="%C(auto)%h%d %s %C(black)%C(bold)%cr"'

" [Tags] Command to generate tags file
let g:fzf_tags_command = 'ctags -R'

" [Commands] --expect expression for directly executing the command
let g:fzf_commands_expect = 'alt-enter,ctrl-x'
nnoremap mm :FZF<CR>
" }}}


nnoremap ,, :Ag<CR>

" ------------------- coc.nvim setttings --------------
augroup deoplete_vim
  autocmd!
  autocmd InsertLeave,CompleteDone * if pumvisible() == 0 | pclose | endif
augroup END

" Add diagnostic info for https://github.com/itchyny/lightline.vim
let g:lightline = {
      \ 'colorscheme': 'wombat',
      \ 'active': {
      \   'left': [ [ 'mode', 'paste' ],
      \             [ 'cocstatus', 'readonly', 'filename', 'modified' ] ]
      \ },
      \ 'component_function': {
      \   'cocstatus': 'coc#status'
      \ },
      \ }



" Using CocList
" Show all diagnostics
nnoremap <silent> <space>a  :<C-u>CocList diagnostics<cr>
" Manage extensions
nnoremap <silent> <space>e  :<C-u>CocList extensions<cr>
" Show commands
nnoremap <silent> <space>c  :<C-u>CocList commands<cr>
" Find symbol of current document
nnoremap <silent> <space>o  :<C-u>CocList outline<cr>
" Search workspace symbols
nnoremap <silent> <space>s  :<C-u>CocList -I symbols<cr>
" Do default action for next item.
nnoremap <silent> <space>j  :<C-u>CocNext<CR>
" Do default action for previous item.
nnoremap <silent> <space>k  :<C-u>CocPrev<CR>
" Resume latest coc list
nnoremap <silent> <space>p  :<C-u>CocListResume<CR>

let g:airline_section_error = '%{airline#util#wrap(airline#extensions#coc#get_error(),0)}'
let g:airline_section_warning = '%{airline#util#wrap(airline#extensions#coc#get_warning(),0)}'

let g:closetag_filenames = "*.html,*.xhtml,*.phtml,*.erb,*.jsx,*.js"
" }}}

" setting for jsx support
let g:jsx_ext_required = 0

"" Javascript syntax highlighting {{{
let g:javascript_plugin_jsdoc = 1
let g:javascript_plugin_ngdoc = 1
let g:jsx_ext_required = 0 " Allow jsx in normal js files
" }}}


" disable anyfold for large files
let g:LargeFile = 1000000 " file is large if size greater than 1MB
autocmd BufReadPre,BufRead * let f=getfsize(expand("<afile>")) | if f > g:LargeFile || f == -2 | call LargeFile() | endif

" hi Folded term=NONE cterm=NONE
set foldlevel=99
" }}}


set statusline+=%#warningmsg#
set statusline+=%*

" better space for removing spaces
let g:better_whitespace_enabled=1
let g:strip_whitespace_on_save=1

" {{{ Vim-Fugitive
noremap gst :Gstatus <CR>

"" Mapping for removing highlighting
nnoremap <leader><space> :noh <CR>

noremap <leader>o :only<CR>
nnoremap <leader>w :w<CR>
vnoremap <leader>w <Esc>:w<CR>gv
inoremap <leader>w <Esc>:w<CR>

nnoremap <leader>q :wq<CR>
vnoremap <leader>q <Esc>:wq<CR>gv
inoremap <leader>q <Esc>:wq<CR>

nnoremap <leader>c :

" mapping esc key to jk
inoremap jk <esc>
" removing esc key property to learn new mapping
inoremap <esc> <nop>
nnoremap <leader>ev :vsplit $MYVIMRC<cr>

" Search in visula mode
vnoremap // y/\V<C-r>=escape(@",'/\')<CR><CR>

" bind cp command to copy the path of the current file
nnoremap cp :let @+ = expand("%")<CR>

" Buffer related settings {{{
nnoremap <leader>j <C-W><C-J>
nnoremap <leader>k <C-W><C-K>
nnoremap <leader>l <C-W><C-L>
nnoremap <leader>h <C-W><C-H>

"" mapping leader for buffer switch
noremap <Leader>a :bprev<Return>
noremap <Leader>s :bnext<Return>
noremap <Leader>d :bd<Return>
" map <Leader>f :b
nmap <leader>f :NERDTreeFind<CR>
" noremap <leader>q :buffers<Return>:buffer

" Listing all the buffers
noremap <Leader><Enter> :Buffers <CR>
" }}}


" mapping git diff
noremap <leader>g :Gdiff<Return>

" mapping :vnew
noremap <leader>v :vnew<CR>

" next and previous errors
noremap <leader>n :lnext <CR>
noremap <leader>p :lprev <CR>


"" For macro playing
nnoremap <Space> @q


noremap <leader>b :Rg <C-R><C-W><CR>


" Indenting the whole file
nnoremap <leader>i :normal gg=G<cr>

" set marker as fold method in vim
" Vimscript file setttings ---------------------------------- {{{
augroup filetype_vim
  autocmd!
  autocmd FileType vim set foldlevel=0
  autocmd FileType vim setlocal foldmethod=marker
augroup END

augroup vim_rc_write
  autocmd!
  autocmd BufWritePost ~/.config/nvim/init.vim silent! !cp % ~/dotfiles/init.vim

  " Sourcing vimrc when written
  autocmd BufWritePost ~/.config/nvim/*.vim :source $MYVIMRC
augroup END

" }}}

" this is a hack as rails vim syntax highlighting
" was somehow not working in the specs.
augroup ruby_file_with_syntax
  autocmd!
  autocmd BufRead *_spec.rb setlocal syntax=on
  autocmd BufRead **/forms/*.rb setlocal syntax=on
augroup END

" Markdown auto folds {{{
augroup filetype_markdown
  autocmd!
  autocmd FileType markdown set foldlevel=0
  autocmd FileType markdown setlocal foldmethod=marker
augroup END

" For mouse events in vim
set mouse=a
if has("mouse_sgr")
  set ttymouse=sgr
else
  if !has('nvim')
    set ttymouse=xterm2
  endif
end


runtime macros/matchit.vim  " for matching do end in ruby

" Augmenting Ag command using fzf#vim#with_preview function
"   * fzf#vim#with_preview([[options], preview window, [toggle keys...]])
"     * For syntax-highlighting, Ruby and any of the following tools are required:
"       - Highlight: http://www.andre-simon.de/doku/highlight/en/highlight.php
"       - CodeRay: http://coderay.rubychan.de/
"       - Rouge: https://github.com/jneen/rouge
"
" :Ag  - Start fzf with hidden preview window that can be enabled with "?" key
"   :Ag! - Start fzf in fullscreen and display the preview window above

" command! -bang -nargs=* Ag
"   \ call fzf#vim#ag(<q-args>,
"   \                 <bang>0 ? fzf#vim#with_preview('up:60%')
"   \                         : fzf#vim#with_preview('right:50%:hidden', '?'),
"   \                 <bang>0)
"
"

" Similarly, we can apply it to fzf#vim#grep. To use ripgrep instead of ag:
command! -bang -nargs=* Rg
      \ call fzf#vim#grep(
      \   'rg --column --line-number --no-heading --color=always '.shellescape(<q-args>), 1,
      \   <bang>0 ? fzf#vim#with_preview('up:60%')
      \           : fzf#vim#with_preview('right:50%:hidden', '?'),
      \   <bang>0)

" }}}
